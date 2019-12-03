package com.example.izbela.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.izbela.api.response.ChatResponse
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatListRecyclerAdapter(
    private val itemContainer: Int,
    click: (ChatResponse.(View) -> Unit)? = null
) : BaseRecyclerListAdapter<ChatResponse, ChatViewHolder>(click) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                itemContainer,
                parent,
                false
            )
        )
    }
}

class ChatViewHolder(private val view: View) : BaseViewHolder<ChatResponse>(view) {

    override fun bind(model: ChatResponse, click: (ChatResponse.(View) -> Unit)?) {
        view.chatTitleTV.text = model.name
        click?.run {
            view.setOnClickListener { model.click(view) }
        }
    }
}


