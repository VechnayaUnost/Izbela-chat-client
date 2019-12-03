package com.example.izbela.presentation.auth.registration

import com.example.izbela.api.Api
import com.example.izbela.api.request.SingRequest
import com.example.izbela.api.response.UserResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class RegistrationRepository @Inject constructor(private val api: Api) :
    RegistrationProtocol.IRegistrationRepository {
    override fun action(login: String, password: String): Single<Response<UserResponse>> {

        return api.registration(SingRequest(login, password)).subscribeOn(Schedulers.io())
    }

}