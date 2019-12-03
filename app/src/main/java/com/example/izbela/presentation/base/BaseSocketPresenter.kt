package com.example.izbela.presentation.base

import moxy.MvpView
import timber.log.Timber

abstract class BaseSocketPresenter<V : MvpView, R : ISocketRepository>(private val rep: R) :
    BasePresenter<V>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        rep.connect()
        Timber.e("FIRST VIEW ATTACH")
    }

    override fun onDestroy() {
        Timber.e("DESTROY VIEW")
        rep.disconnect()
        super.onDestroy()
    }
}