package com.example.izbela.di.modules

import com.example.izbela.di.FragmentScope
import com.example.izbela.presentation.chat.chat.ChatProtocol
import com.example.izbela.presentation.chat.chat.ChatRepository
import com.example.izbela.presentation.chat.chatlist.ChatListProtocol
import com.example.izbela.presentation.chat.chatlist.ChatListRepository
import com.example.izbela.presentation.chat.createchat.CreateChatProtocol
import com.example.izbela.presentation.chat.createchat.CreateChatRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ChatModule {

    @Binds
    @FragmentScope
    abstract fun bindRepList(chatListRepository: ChatListRepository): ChatListProtocol.IChatListRepository

    @Binds
    @FragmentScope
    abstract fun bindRepCreate(chatListRepository: CreateChatRepository): CreateChatProtocol.ICreateChatRepository

    @Binds
    @FragmentScope
    abstract fun bindChat(chatRepository: ChatRepository): ChatProtocol.IChatRepository
}