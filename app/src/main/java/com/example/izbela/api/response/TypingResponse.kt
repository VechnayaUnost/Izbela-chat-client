package com.example.izbela.api.response

import org.json.JSONObject

class TypingResponse(val user: UserResponse, roomId: String, text: String)

fun JSONObject.toTypingResponse(): TypingResponse {
    return TypingResponse(
        getJSONObject("user").toUser(),
        getString("roomId"),
        getString("text")
    )
}