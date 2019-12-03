package com.example.izbela.presentation.chat.createchat

import com.example.izbela.presentation.base.ISocketRepository
import io.reactivex.Observable
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CreateChatProtocol {

    interface IChatListView : MvpView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun createSuccess()
    }

    interface ICreateChatRepository : ISocketRepository {

        fun create(name: String)

        val actionCreated: Observable<Unit>
    }
}