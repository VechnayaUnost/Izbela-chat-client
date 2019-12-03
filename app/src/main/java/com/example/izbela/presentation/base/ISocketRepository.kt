package com.example.izbela.presentation.base

interface ISocketRepository {

    fun connect()
    fun disconnect()
    fun onConnected(){}
}