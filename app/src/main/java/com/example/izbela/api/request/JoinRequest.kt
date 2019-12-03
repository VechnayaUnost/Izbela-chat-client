package com.example.izbela.api.request

import org.json.JSONObject

class JoinRequest(val id: String)

fun JoinRequest.toJSON(): JSONObject {
    return JSONObject().apply {
        put("id", id)
    }
}

