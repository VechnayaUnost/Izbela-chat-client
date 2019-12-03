package com.example.izbela.api.response

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class UserResponse(
    val id: String,
    @SerializedName("username")
    val userName: String
)

fun JSONObject.toUser(): UserResponse {
    return UserResponse(
        getString("id"),
        getString("username")
    )
}