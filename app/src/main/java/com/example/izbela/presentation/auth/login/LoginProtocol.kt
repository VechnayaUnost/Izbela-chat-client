package com.example.izbela.presentation.auth.login

import com.example.izbela.presentation.auth.AuthProtocol

interface LoginProtocol {

    interface ILoginRepository : AuthProtocol.IAuthRepository

}