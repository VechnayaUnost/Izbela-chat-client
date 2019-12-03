package com.example.izbela.utils

import android.content.Context
import javax.inject.Inject

private const val ACCESS_TOKEN_PREFERENCES: String = "ACCESS_TOKEN_PREFERENCES"

class SharedPreferencesUtil @Inject constructor(private val context: Context) :
    ISharedPreferencesUtil {

    override fun setToken(accessToken: String) {
        context.getSharedPreferences(ACCESS_TOKEN_PREFERENCES, Context.MODE_PRIVATE).edit()
            .putString(ACCESS_TOKEN_PREFERENCES, accessToken).apply()
    }

    override fun getToken(): String? {
        return context.getSharedPreferences(ACCESS_TOKEN_PREFERENCES, Context.MODE_PRIVATE)
            .getString(ACCESS_TOKEN_PREFERENCES, null)
    }

}

interface ISharedPreferencesUtil {
    fun getToken(): String?
    fun setToken(accessToken: String)
}
