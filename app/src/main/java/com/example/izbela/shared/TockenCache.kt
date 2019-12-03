package com.example.izbela.shared

import com.example.izbela.utils.ISharedPreferencesUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenCache @Inject constructor(private val sharedPreferences: ISharedPreferencesUtil) :
    ITokenCache {

    override var accessToken: String? = sharedPreferences.getToken()

    override fun onNewAccessToken(accessToken: String) {
        this.accessToken = accessToken
        sharedPreferences.setToken(accessToken)
    }


}

interface ITokenCache {

    var accessToken: String?

    fun onNewAccessToken(accessToken: String)
}