package com.example.izbela.api

import com.example.izbela.api.request.SingRequest
import com.example.izbela.api.response.UserResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    companion object {

        private const val LOGIN_ENDPOINT = "user/login"

        private const val REGISTRATION_ENDPOINT = "user/register"

        private const val USER_ENDPOINT = "user"

    }

    @POST(LOGIN_ENDPOINT)
    fun login(@Body body: SingRequest): Single<Response<UserResponse>>

    @POST(REGISTRATION_ENDPOINT)
    fun registration(@Body body: SingRequest): Single<Response<UserResponse>>

    @GET(USER_ENDPOINT)
    fun getUser():Single<Response<UserResponse>>
}