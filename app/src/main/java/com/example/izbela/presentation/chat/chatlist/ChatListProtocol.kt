package com.example.izbela.presentation.chat.chatlist

import com.example.izbela.api.response.ChatResponse
import com.example.izbela.presentation.base.ISocketRepository
import io.reactivex.subjects.BehaviorSubject
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ChatListProtocol {

    interface IChatListView : MvpView

    interface IChatListRepository : ISocketRepository {

        val chats: BehaviorSubject<List<ChatResponse>>

    }
}