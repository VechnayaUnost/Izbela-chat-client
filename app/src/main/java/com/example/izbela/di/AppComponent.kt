package com.example.izbela.di

import com.example.izbela.App
import com.example.izbela.di.modules.AppModule
import com.example.izbela.di.modules.GlobalModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, GlobalModule::class])
interface MainComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        fun build(): MainComponent
        fun globalModule(context: GlobalModule): Builder
    }

}