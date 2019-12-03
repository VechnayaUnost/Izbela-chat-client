package com.example.izbela.presentation.chat.chat

import com.example.izbela.api.response.ChatResponse
import com.example.izbela.api.response.MessageResponse
import com.example.izbela.api.response.TypingResponse
import com.example.izbela.api.response.UserResponse
import com.example.izbela.presentation.base.ISocketRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import moxy.MvpView

interface ChatProtocol {

    interface IChatView : MvpView

    interface IChatRepository : ISocketRepository {
        fun setChat(chatResponse: ChatResponse)

        val messages: Observable<List<MessageResponse>>

        val typing: Observable<ChatRepository.TypeState>

        fun emitTyping()

        fun emitMessage(text:String)
    }
}