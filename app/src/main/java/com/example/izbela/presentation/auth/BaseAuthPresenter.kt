package com.example.izbela.presentation.auth

import com.example.izbela.navigation.Screens
import com.example.izbela.presentation.base.BasePresenter
import com.example.izbela.shared.ITokenCache
import com.example.izbela.shared.IUserCache
import com.jakewharton.rxbinding3.InitialValueObservable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber

private const val AUTHORIZATION = "AUTHORIZATION"

abstract class BaseAuthPresenter<V : AuthProtocol.IAuthView, R : AuthProtocol.IAuthRepository>(
    private val rep: AuthProtocol.IAuthRepository,
    private val tokenCache: ITokenCache,
    private val userCache: IUserCache,
    private val cicerone: Cicerone<Router>
) : BasePresenter<AuthProtocol.IAuthView>() {

    fun listenFields(
        login: InitialValueObservable<CharSequence>,
        password: InitialValueObservable<CharSequence>
    ) {
        Observable.combineLatest(
            login.skipInitialValue(),
            password.skipInitialValue(),
            BiFunction<CharSequence, CharSequence, Boolean> { t1, t2 ->
                return@BiFunction (!t1.isBlank() && !t2.isBlank())
            })
            .subscribeOn(Schedulers.io())
            .subscribe {
                viewState.validateFields(it)
            }
            .addDisposable()
    }

    fun action(login: String, password: String) {
        rep.action(login, password)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress(true) }
            .doFinally { viewState.showProgress(false) }
            .subscribe({
                if (it.isSuccessful) {
                    it.body()?.run {
                        userCache.newUser(this)
                    }
                    tokenCache.onNewAccessToken(it.headers()[AUTHORIZATION] ?: "")
                    cicerone.router.replaceScreen(Screens.ChatListScreen())
                } else {
                    viewState.failed()
                }
            }, {
                Timber.e(it)
            }).addDisposable()
    }
}