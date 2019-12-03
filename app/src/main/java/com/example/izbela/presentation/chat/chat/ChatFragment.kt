package com.example.izbela.presentation.chat.chat

import android.os.Bundle
import com.example.izbela.R
import com.example.izbela.api.response.ChatResponse
import com.example.izbela.presentation.base.BaseMvpFragment
import com.jakewharton.rxbinding3.widget.textChanges
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_chat.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import javax.inject.Inject

class ChatFragment : BaseMvpFragment<ChatPresenter, ChatProtocol.IChatView>(),
    ChatProtocol.IChatView {

    override val layoutId: Int = R.layout.fragment_chat

    @Inject
    override lateinit var daggerPresenter: Lazy<ChatPresenter>

    private var chatResponse: ChatResponse? = null

    @InjectPresenter
    lateinit var presenter: ChatPresenter

    @ProvidePresenter
    override fun providePresenter(): ChatPresenter {
        chatResponse = arguments?.getParcelable(ChatResponse::class.java.name) as? ChatResponse
        Timber.e(chatResponse.toString())
        return daggerPresenter.get().apply { chat = chatResponse }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        messageRv.adapter = presenter.adapter
        toolbar?.title = chatResponse?.name

        presenter.listenField(messageET.textChanges())

        sendMessageBtm.setOnClickListener {
            presenter.sendMessage(messageET.text.toString())
            messageET.setText("")
        }
    }
}