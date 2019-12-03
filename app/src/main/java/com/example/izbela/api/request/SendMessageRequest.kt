package com.example.izbela.api.request

import org.json.JSONObject

data class SendMessageRequest(val text: String, val roomId: String)

fun SendMessageRequest.toJSON(): JSONObject {
    return JSONObject().apply {
        put("text", text)
        put("roomId", roomId)
    }
}