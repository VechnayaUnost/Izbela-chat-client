package com.example.izbela.di.modules

import android.content.Context
import com.example.izbela.di.CiceroneModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule::class, SharedModule::class, CiceroneModule::class])
class GlobalModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }
}