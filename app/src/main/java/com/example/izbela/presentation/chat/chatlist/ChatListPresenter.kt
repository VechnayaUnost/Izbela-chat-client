package com.example.izbela.presentation.chat.chatlist

import android.view.View
import com.example.izbela.R
import com.example.izbela.adapter.ChatListRecyclerAdapter
import com.example.izbela.api.response.ChatResponse
import com.example.izbela.navigation.Screens
import com.example.izbela.presentation.base.BaseSocketPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject


@InjectViewState
class ChatListPresenter @Inject constructor(
    private val rep: ChatListProtocol.IChatListRepository,
    private val cicerone: Cicerone<Router>
) : BaseSocketPresenter<ChatListProtocol.IChatListView, ChatListProtocol.IChatListRepository>(rep) {

    fun createChat() {
        cicerone.router.navigateTo(Screens.CreateChatScreen())
    }

    private val click: ChatResponse.(View) -> Unit = {
        cicerone.router.navigateTo(Screens.ChatScreen(this))
    }

    val adapter = ChatListRecyclerAdapter(R.layout.item_chat, click)

    override fun attachView(view: ChatListProtocol.IChatListView) {
        super.attachView(view)
        rep.chats.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter.submitList(it) }
            .addDisposable()
    }
}