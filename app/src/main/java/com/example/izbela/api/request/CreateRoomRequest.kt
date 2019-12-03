package com.example.izbela.api.request

import org.json.JSONObject

data class CreateRoomRequest(val name: String)

fun CreateRoomRequest.jsonObject(): JSONObject {
    return JSONObject().apply {
        put("name", name)
    }
}