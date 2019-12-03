package com.example.izbela.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : ItemRecyclerModel>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(model: T, click: (T.(View) -> Unit)?)

}