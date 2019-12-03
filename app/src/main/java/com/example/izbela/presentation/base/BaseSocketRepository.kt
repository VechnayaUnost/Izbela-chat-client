package com.example.izbela.presentation.base

import com.example.izbela.shared.ITokenCache
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import timber.log.Timber

private const val SOCKET_URL = "https://socckets.herokuapp.com/"

abstract class BaseSocketRepository(private val tokenCache: ITokenCache) : ISocketRepository {

    protected lateinit var socket: Socket

    abstract val socketMap: Map<String, Emitter.Listener>

    private val onConnect = Emitter.Listener {
        it.forEach {
            Timber.e("connect $it")
        }
    }
    private val onDisconnect = Emitter.Listener {
        it.forEach {
            Timber.e("disc $it")
        }
    }
    private val onConnectError = Emitter.Listener {
        it.forEach {
            Timber.e("connect err $it")
        }
    }

    private val defaultListener = mapOf(
        Socket.EVENT_CONNECT to onConnect,
        Socket.EVENT_DISCONNECT to onDisconnect,
        Socket.EVENT_CONNECT_ERROR to onConnectError,
        Socket.EVENT_CONNECT_TIMEOUT to onConnectError
    )

    override fun connect() {
        val options = IO.Options()
        options.reconnection = false
        options.forceNew = false

        options.query = "token=${tokenCache.accessToken}"
        tokenCache.accessToken?.let {
            socket = IO.socket(SOCKET_URL, options)
        }

        defaultListener.forEach { pair ->
            val (event, listener) = pair
            socket.on(event, listener)
        }

        socketMap.forEach { pair ->
            val (event, listener) = pair
            socket.on(event, listener)
        }

        socket.connect()
        onConnected()
    }

    override fun disconnect() {
        socketMap.forEach { pair ->
            val (event, listener) = pair
            socket.off(event, listener)
        }

        defaultListener.forEach { pair ->
            val (event, listener) = pair
            socket.off(event, listener)
        }

        socket.disconnect()
        socket.close()
    }

}