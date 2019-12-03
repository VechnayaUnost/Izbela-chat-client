package com.example.izbela.presentation.auth.login

import com.example.izbela.navigation.Screens
import com.example.izbela.presentation.auth.AuthProtocol
import com.example.izbela.presentation.auth.BaseAuthPresenter
import com.example.izbela.shared.ITokenCache
import com.example.izbela.shared.IUserCache
import moxy.InjectViewState
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    loginRepository: LoginProtocol.ILoginRepository,
    private val cicerone: Cicerone<Router>,
    tokenCache: ITokenCache,
    userCache: IUserCache
) : BaseAuthPresenter<AuthProtocol.IAuthView, LoginProtocol.ILoginRepository>(
    loginRepository,
    tokenCache,
    userCache,
    cicerone
) {

    fun registration() {
        cicerone.router.navigateTo(Screens.RegistrationScreen())
    }
}