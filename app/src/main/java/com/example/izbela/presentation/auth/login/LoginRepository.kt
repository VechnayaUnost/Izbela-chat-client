package com.example.izbela.presentation.auth.login

import com.example.izbela.api.Api
import com.example.izbela.api.request.SingRequest
import com.example.izbela.api.response.UserResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: Api) : LoginProtocol.ILoginRepository {

    override fun action(login: String, password: String): Single<Response<UserResponse>> {
        return api.login(SingRequest(login, password)).subscribeOn(Schedulers.io())
    }
}