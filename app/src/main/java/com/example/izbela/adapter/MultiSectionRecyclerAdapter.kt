package com.example.izbela.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.izbela.presentation.chat.chat.ChatPresenter
import com.example.izbela.presentation.chat.chat.ChatRepository
import kotlinx.android.synthetic.main.item_my_message.view.*
import kotlinx.android.synthetic.main.item_user_message.view.*

class MultiSectionRecyclerAdapter<T : ChatPresenter.ChatEvents>(
    private val classHolder: Map<Class<out T>, Pair<Int, (View) -> MessageViewHolder<T>>>,
    private val clickFollow: Map<Class<out T>, (ChatPresenter.ChatEvents.(View) -> Unit)?>? = null
) : ListAdapter<T, MessageViewHolder<T>>(diffUtil<T>()) {

    override fun onBindViewHolder(holder: MessageViewHolder<T>, position: Int) {
        val viewType = getItem(position)?.let {
            it::class.java
        } ?: throw UnsupportedOperationException("this view type unsupoprted")
        holder.bind(getItem(position) as T, clickFollow?.get(viewType))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )
        return MessageViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val viewType = getItem(position)?.let {
            it::class.java
        } ?: throw UnsupportedOperationException("this view type unsupported")
        return classHolder[viewType]?.first
            ?: throw UnsupportedOperationException("this view type unsupported")
    }
}

class MessageViewHolder<T : ChatPresenter.ChatEvents>(private val view: View) :
    BaseViewHolder<T>(view) {
    override fun bind(model: T, click: (T.(View) -> Unit)?) {
        when (model) {
            is ChatPresenter.ChatEvents.MyMessage -> {
                view.visibility = View.VISIBLE
                view.myUserNameTV.text = model.message.user.userName
                view.myUserMessageTV.text = model.message.text
            }
            is ChatPresenter.ChatEvents.OtherUserMessage -> {
                view.visibility = View.VISIBLE
                view.userNameTV.text = model.message.user.userName
                view.userNameMessageTV.text = model.message.text
            }

            is ChatPresenter.ChatEvents.Typing -> {
                if (model.typing is ChatRepository.TypeState.NotType) {
                    view.visibility = View.GONE
                }
                if (model.typing is ChatRepository.TypeState.Type) {
                    view.visibility = View.VISIBLE
                    view.userNameTV.text = model.typing.typingResponse.user.userName
                    view.userNameMessageTV.text = "Typing ..."
                }
            }
        }
    }
}
