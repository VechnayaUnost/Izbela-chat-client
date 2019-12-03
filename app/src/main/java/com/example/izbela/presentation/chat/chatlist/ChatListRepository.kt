package com.example.izbela.presentation.chat.chatlist

import com.example.izbela.api.response.ChatResponse
import com.example.izbela.api.response.toChat
import com.example.izbela.presentation.base.BaseSocketRepository
import com.example.izbela.shared.ITokenCache
import io.reactivex.subjects.BehaviorSubject
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

private const val ROOMS_SOCKET = "rooms"

class ChatListRepository @Inject constructor(tokenCache: ITokenCache) :
    BaseSocketRepository(tokenCache), ChatListProtocol.IChatListRepository {

    private val socketListener = Emitter.Listener {
        val messageList = ArrayList<ChatResponse>()
        val chatArray = (it[0] as? JSONArray)
        val chatCount = chatArray?.length() ?: 0

        for (i in 0 until chatCount) {
            (chatArray?.get(i) as? JSONObject)?.toChat()?.let { it1 -> messageList.add(it1) }
        }

        chats.onNext(messageList.toList())
    }

    override val socketMap: Map<String, Emitter.Listener> = mapOf(ROOMS_SOCKET to socketListener)

    override val chats = BehaviorSubject.create<List<ChatResponse>>()

}
