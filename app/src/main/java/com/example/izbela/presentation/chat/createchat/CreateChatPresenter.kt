package com.example.izbela.presentation.chat.createchat

import com.example.izbela.presentation.base.BaseSocketPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CreateChatPresenter @Inject constructor(
    private val rep: CreateChatProtocol.ICreateChatRepository,
    private val cicerone: Cicerone<Router>
) : BaseSocketPresenter<CreateChatProtocol.IChatListView, CreateChatProtocol.ICreateChatRepository>(
    rep
) {

    fun create(name: String) {
        rep.create(name)
    }

    override fun attachView(view: CreateChatProtocol.IChatListView) {
        super.attachView(view)

        rep.actionCreated
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewState.createSuccess()
                cicerone.router.exit()
            }.addDisposable()
    }
}