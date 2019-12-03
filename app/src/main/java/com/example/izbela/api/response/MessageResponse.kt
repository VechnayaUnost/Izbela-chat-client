package com.example.izbela.api.response

import com.example.izbela.adapter.ItemRecyclerModel
import org.json.JSONObject

data class MessageResponse(
    override val id: String,
    val text: String,
    val user: UserResponse,
    val roomId: String
):ItemRecyclerModel

fun JSONObject.toMessage(): MessageResponse {
    return MessageResponse(
        getString("id"),
        getString("text"),
        getJSONObject("user").toUser(),
        getString("roomId")
    )
}