package com.example.izbela.presentation.chat.chat

import android.view.View
import com.example.izbela.R
import com.example.izbela.adapter.ItemRecyclerModel
import com.example.izbela.adapter.MessageViewHolder
import com.example.izbela.adapter.MultiSectionRecyclerAdapter
import com.example.izbela.api.response.ChatResponse
import com.example.izbela.api.response.MessageResponse
import com.example.izbela.api.response.UserResponse
import com.example.izbela.presentation.base.BaseSocketPresenter
import com.example.izbela.shared.IUserCache
import com.jakewharton.rxbinding3.InitialValueObservable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class ChatPresenter @Inject constructor(
    private val rep: ChatProtocol.IChatRepository,
    userCache: IUserCache
) :
    BaseSocketPresenter<ChatProtocol.IChatView, ChatProtocol.IChatRepository>(rep) {

    var chat: ChatResponse? = null

    override fun onFirstViewAttach() {
        chat?.run {
            rep.setChat(this)
        }
        super.onFirstViewAttach()
    }

    val adapter = MultiSectionRecyclerAdapter(
        mapOf(
            ChatEvents.OtherUserMessage::class.java to Pair(
                R.layout.item_user_message,
                { view: View -> MessageViewHolder<ChatEvents>(view) }),
            ChatEvents.MyMessage::class.java to Pair(
                R.layout.item_my_message,
                { view: View -> MessageViewHolder<ChatEvents>(view) }),
            ChatEvents.Typing::class.java to Pair(
                R.layout.item_user_message,
                { view: View -> MessageViewHolder<ChatEvents>(view) })
        )
    )

    fun listenField(textChanges: InitialValueObservable<CharSequence>) {
        textChanges.skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribe {
                rep.emitTyping()
            }.addDisposable()
    }

    fun sendMessage(text: String) {
        rep.emitMessage(text)
    }

    init {
        Observable.combineLatest(
            rep.messages,
            userCache.user,
            rep.typing,
            Function3<List<MessageResponse>,
                    UserResponse,
                    ChatRepository.TypeState,
                    Triple<List<MessageResponse>, UserResponse, ChatRepository.TypeState>> { messages, user, state ->
                return@Function3 Triple(messages, user, state)
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map<List<ChatEvents>> { (messages, user, state) ->
                val list: MutableList<ChatEvents> = messages.map {
                    if (it.user.id == user.id) {
                        ChatEvents.MyMessage(it)
                    } else {
                        ChatEvents.OtherUserMessage(it)
                    }
                }.toMutableList()

                val type = when (state) {
                    is ChatRepository.TypeState.Type -> {
                        if (state.typingResponse.user.id != user.id) {
                            ChatEvents.Typing(state)
                        } else {
                            ChatEvents.Typing(ChatRepository.TypeState.NotType(state.typingResponse))
                        }
                    }
                    is ChatRepository.TypeState.NotType -> ChatEvents.Typing(
                        ChatRepository.TypeState.NotType(
                            state.typingResponse
                        )
                    )

                }
                list.add(type)
                return@map list
            }
            .subscribe {
                adapter.submitList(it)
            }
            .addDisposable()
    }


    sealed class ChatEvents : ItemRecyclerModel {
        data class MyMessage(val message: MessageResponse, override val id: String = message.id) :
            ChatEvents()

        data class OtherUserMessage(
            val message: MessageResponse,
            override val id: String = message.id
        ) : ChatEvents()

        data class Typing(
            val typing: ChatRepository.TypeState, override val id: String = typing.id
        ) : ChatEvents()
    }
}