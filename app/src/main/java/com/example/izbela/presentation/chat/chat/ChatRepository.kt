package com.example.izbela.presentation.chat.chat

import com.example.izbela.adapter.ItemRecyclerModel
import com.example.izbela.api.request.JoinRequest
import com.example.izbela.api.request.SendMessageRequest
import com.example.izbela.api.request.TypeMessageRequest
import com.example.izbela.api.request.toJSON
import com.example.izbela.api.response.*
import com.example.izbela.presentation.base.BaseSocketRepository
import com.example.izbela.shared.ITokenCache
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val SOCKET_CHAT = "room/join"

class ChatRepository @Inject constructor(
    tokenCache: ITokenCache
) : BaseSocketRepository(tokenCache), ChatProtocol.IChatRepository {


    sealed class TypeState : ItemRecyclerModel {
        data class Type(
            val typingResponse: TypingResponse,
            override val id: String = typingResponse.user.id
        ) : TypeState()

        data class NotType(
            val typingResponse: TypingResponse?,
            override val id: String = typingResponse?.user?.id ?: ""
        ) : TypeState()
    }

    override val typing = BehaviorSubject.createDefault<TypeState>(TypeState.NotType(null, ""))

    override val messages = BehaviorSubject.create<List<MessageResponse>>()

    private var chatId: String? = null

    private val localDispose = CompositeDisposable()

    override fun setChat(chatResponse: ChatResponse) {
        chatId = chatResponse.id
    }

    private val chatEmitter = Emitter.Listener {
        val messageList = ArrayList<MessageResponse>()
        val chatArray = (it[0] as? JSONArray)
        val chatCount = chatArray?.length() ?: 0

        for (i in 0 until chatCount) {
            (chatArray?.get(i) as? JSONObject)?.toMessage()
                ?.let { messages -> messageList.add(messages) }
        }

        messages.onNext(messageList.toList())
    }

    private val chatTypingEmitter = Emitter.Listener {
        it.forEach {
            Timber.e(it.toString())
        }
    }

    private val chatTypingLister = Emitter.Listener {
        localDispose.clear()
        (it?.get(0) as? JSONObject)?.toTypingResponse()?.run {
            typing.onNext(TypeState.Type(this))
            Single.timer(1000, TimeUnit.MILLISECONDS)
                .doOnSubscribe { localDispose.add(it) }
                .flatMap {
                    typing.firstOrError()

                }.subscribe { type ->
                    if (type is TypeState.Type) {
                        typing.onNext(TypeState.NotType(type.typingResponse, type.id))
                    }
                }
        }
    }

    override fun onConnected() {
        chatId?.run {
            socket.emit(SOCKET_CHAT, JoinRequest(this).toJSON())
        } ?: throw NullPointerException("ID IS NULL")
    }

    private val messageEmitter = Emitter.Listener {
        val messageList = ArrayList<MessageResponse>()
        val chatArray = (it[0] as? JSONArray)

        val chatCount = chatArray?.length() ?: 0

        for (i in 0 until chatCount) {
            (chatArray?.get(i) as? JSONObject)?.toMessage()
                ?.let { messages -> messageList.add(messages) }
        }

        messages.onNext(messageList.toList())
    }

    override fun emitTyping() {
        socket.emit("room/typing", TypeMessageRequest(chatId ?: "").toJSON())
    }

    override fun emitMessage(text: String) {
        socket.emit("message", SendMessageRequest(text, chatId ?: "").toJSON())
    }

    private val joinEmitter = Emitter.Listener { }

    override val socketMap: Map<String, Emitter.Listener>
        get() = mapOf(
            "room/" + (chatId ?: throw NullPointerException("ID IS NULL")) to chatEmitter,
            SOCKET_CHAT to joinEmitter,
            "room/typing" to chatTypingEmitter,
            "room/$chatId/typing" to chatTypingLister,
            "message" to messageEmitter
        )
}