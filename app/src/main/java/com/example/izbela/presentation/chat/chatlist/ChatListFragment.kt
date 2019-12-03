package com.example.izbela.presentation.chat.chatlist

import android.os.Bundle
import com.example.izbela.R
import com.example.izbela.presentation.auth.login.LoginPresenter
import com.example.izbela.presentation.base.BaseMvpFragment
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_chat_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class ChatListFragment : BaseMvpFragment<ChatListPresenter, ChatListProtocol.IChatListView>(),
    ChatListProtocol.IChatListView {

    @Inject
    override lateinit var daggerPresenter: Lazy<ChatListPresenter>

    @InjectPresenter
    lateinit var presenter: ChatListPresenter

    @ProvidePresenter
    override fun providePresenter(): ChatListPresenter {
        return daggerPresenter.get()
    }

    override var titleId: Int? = R.string.chat_list_title

    override val layoutId: Int = R.layout.fragment_chat_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createChatFab.setOnClickListener {
            presenter.createChat()
        }

        chatListRV.adapter = presenter.adapter
    }

}