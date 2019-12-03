package com.example.izbela.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseRecyclerListAdapter<T : ItemRecyclerModel, VH : BaseViewHolder<T>>(
    private val click: (T.(View) -> Unit)? = null
) : ListAdapter<T, VH>(diffUtil<T>()) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), click)
    }
}

fun <D : ItemRecyclerModel> diffUtil() = object : DiffUtil.ItemCallback<D>() {
    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
        return oldItem == newItem
    }
}