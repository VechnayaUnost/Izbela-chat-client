package com.example.izbela.di.modules

import com.example.izbela.MainActivity
import com.example.izbela.di.ActivityScope
import com.example.izbela.di.FragmentScope
import com.example.izbela.di.modules.auth.AuthModule
import com.example.izbela.presentation.auth.login.LoginFragment
import com.example.izbela.presentation.auth.registration.RegistrationFragment
import com.example.izbela.presentation.chat.chat.ChatFragment
import com.example.izbela.presentation.chat.chatlist.ChatListFragment
import com.example.izbela.presentation.chat.createchat.CreateChatFragment
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector


@Module(includes = [AndroidInjectionModule::class])
abstract class AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

    @Module
    abstract class MainActivityModule {

        @FragmentScope
        @ContributesAndroidInjector(modules = [AuthModule::class])
        abstract fun loginFragment(): LoginFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = [AuthModule::class])
        abstract fun registrationFragment(): RegistrationFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = [ChatModule::class])
        abstract fun chatListFragment(): ChatListFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = [ChatModule::class])
        abstract fun createChatFragment(): CreateChatFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = [ChatModule::class])
        abstract fun chatFragment(): ChatFragment
    }
}