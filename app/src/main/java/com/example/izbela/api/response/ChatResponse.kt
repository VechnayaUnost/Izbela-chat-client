package com.example.izbela.api.response

import android.os.Parcelable
import com.example.izbela.adapter.ItemRecyclerModel
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class ChatResponse(
    override val id: String,
    val name: String
) : ItemRecyclerModel, Parcelable

fun JSONObject.toChat(): ChatResponse {
    return ChatResponse(
        getString("id"),
        getString("name")
    )
}