package com.example.izbela.api.request

import org.json.JSONObject

data class TypeMessageRequest(val id: String)

fun TypeMessageRequest.toJSON(): JSONObject {
    return JSONObject().apply {
        put("id",id)
    }
}