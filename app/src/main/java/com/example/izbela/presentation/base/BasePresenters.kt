package com.example.izbela.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import moxy.MvpPresenter
import moxy.MvpView


abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    fun Disposable.addDisposable() {
        compositeDisposable.add(this)
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        super.detachView(view)
    }
}