package com.example.izbela.di.modules.auth

import com.example.izbela.presentation.auth.login.LoginProtocol
import com.example.izbela.presentation.auth.login.LoginRepository
import com.example.izbela.presentation.auth.registration.RegistrationProtocol
import com.example.izbela.presentation.auth.registration.RegistrationRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AuthModule {

    @Binds
    abstract fun bindLoginRep(rep: LoginRepository): LoginProtocol.ILoginRepository

    @Binds
    abstract fun bindRegisterRep(rep: RegistrationRepository): RegistrationProtocol.IRegistrationRepository
}