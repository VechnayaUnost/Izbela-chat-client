package com.example.izbela.presentation.auth.registration

import com.example.izbela.presentation.auth.AuthProtocol
import com.example.izbela.presentation.auth.BaseAuthPresenter
import com.example.izbela.shared.ITokenCache
import com.example.izbela.shared.IUserCache
import moxy.InjectViewState
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RegistrationPresenter @Inject constructor(
    regRep: RegistrationProtocol.IRegistrationRepository,
    tokenCache: ITokenCache,
    userCache: IUserCache,
    cicerone: Cicerone<Router>
) : BaseAuthPresenter<AuthProtocol.IAuthView, RegistrationProtocol.IRegistrationRepository>(
    regRep,
    tokenCache,
    userCache,
    cicerone
)