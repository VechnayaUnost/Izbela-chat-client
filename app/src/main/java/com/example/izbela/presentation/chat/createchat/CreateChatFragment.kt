package com.example.izbela.presentation.chat.createchat

import android.os.Bundle
import android.widget.Toast
import com.example.izbela.R
import com.example.izbela.presentation.base.BaseMvpFragment
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_create_chat.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class CreateChatFragment : BaseMvpFragment<CreateChatPresenter, CreateChatProtocol.IChatListView>(),
    CreateChatProtocol.IChatListView {

    @Inject
    override lateinit var daggerPresenter: Lazy<CreateChatPresenter>

    @InjectPresenter
    lateinit var presenter: CreateChatPresenter

    @ProvidePresenter
    override fun providePresenter(): CreateChatPresenter {
        return daggerPresenter.get()
    }

    override val layoutId: Int = R.layout.fragment_create_chat

    override var titleId: Int? = R.string.chat_create_title

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        createChatBtn.setOnClickListener {
            presenter.create(chatNameEt.text?.toString() ?: "Empty room name")
        }
    }

    override fun createSuccess() {
        Toast.makeText(requireContext(), "Чат создан", Toast.LENGTH_SHORT).show()
    }

}