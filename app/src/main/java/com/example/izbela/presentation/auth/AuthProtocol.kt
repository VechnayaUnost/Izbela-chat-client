package com.example.izbela.presentation.auth

import com.example.izbela.api.response.UserResponse
import io.reactivex.Single
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import retrofit2.Response

interface AuthProtocol {

    interface IAuthView : MvpView {

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun failed()

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun validateFields(enabled: Boolean)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showProgress(show: Boolean)
    }

    interface IAuthRepository {

        fun action(login: String, password: String): Single<Response<UserResponse>>
    }
}