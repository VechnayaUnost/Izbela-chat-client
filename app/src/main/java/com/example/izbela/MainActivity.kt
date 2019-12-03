package com.example.izbela

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.izbela.api.Api
import com.example.izbela.navigation.Navigator
import com.example.izbela.navigation.Screens
import com.example.izbela.shared.ITokenCache
import com.example.izbela.shared.IUserCache
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable, HasAndroidInjector {

    override fun androidInjector(): DispatchingAndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var ciceroneFactory: Cicerone<Router>

    @Inject
    lateinit var tokenCache: ITokenCache

    @Inject
    lateinit var userCache: IUserCache

    @Inject
    lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            if (!tokenCache.accessToken.isNullOrBlank()) {
                api.getUser().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        it.body()?.let { it1 -> userCache.newUser(it1) }
                        ciceroneFactory.router.newRootScreen(Screens.ChatListScreen())
                    }, {
                        ciceroneFactory.router.newRootScreen(Screens.ChatListScreen())
                    })
            } else {
                ciceroneFactory.router.newRootScreen(Screens.LoginScreen())
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        ciceroneFactory.navigatorHolder.setNavigator(
            Navigator(
                this,
                supportFragmentManager,
                R.id.fragment_container
            )
        )
    }

    override fun onPause() {
        super.onPause()
        ciceroneFactory.navigatorHolder.removeNavigator()
    }
}
