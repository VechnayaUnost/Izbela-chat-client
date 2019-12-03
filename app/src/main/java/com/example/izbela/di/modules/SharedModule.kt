package com.example.izbela.di.modules

import com.example.izbela.shared.*
import com.example.izbela.utils.ISharedPreferencesUtil
import com.example.izbela.utils.SharedPreferencesUtil
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class SharedModule {
    @Binds
    @Singleton
    abstract fun bindTokenCache(tokenCache: TokenCache): ITokenCache

    @Binds
    @Singleton
    abstract fun bindSharePreferences(shared: SharedPreferencesUtil): ISharedPreferencesUtil

    @Binds
    @Singleton
    abstract fun bindUserCache(userCache: UserCache): IUserCache
}