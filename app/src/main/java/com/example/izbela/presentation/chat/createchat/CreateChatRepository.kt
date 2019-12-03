package com.example.izbela.presentation.chat.createchat

import com.example.izbela.api.request.CreateRoomRequest
import com.example.izbela.api.request.jsonObject
import com.example.izbela.presentation.base.BaseSocketRepository
import com.example.izbela.shared.ITokenCache
import io.reactivex.subjects.PublishSubject
import io.socket.emitter.Emitter
import timber.log.Timber
import javax.inject.Inject

private const val ROOM_CREATE = "room/new"
private const val ROOM_ALL = "rooms"

class CreateChatRepository @Inject constructor(tokenCache: ITokenCache) :
    BaseSocketRepository(tokenCache),
    CreateChatProtocol.ICreateChatRepository {

    private var created = false

    override val actionCreated = PublishSubject.create<Unit>()

    private val createChatListener = Emitter.Listener {}

    private val chatsChanged = Emitter.Listener {
        Timber.e("CHAT CHANGED")
        if (created) {
            actionCreated.onNext(Unit)
        }
    }

    override fun create(name: String) {
        created = true
        socket.emit(ROOM_CREATE, CreateRoomRequest(name).jsonObject())
    }

    override val socketMap: Map<String, Emitter.Listener> = mapOf(
        ROOM_CREATE to createChatListener,
        ROOM_ALL to chatsChanged
    )

}