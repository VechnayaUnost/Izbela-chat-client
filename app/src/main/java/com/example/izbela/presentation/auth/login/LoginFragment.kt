package com.example.izbela.presentation.auth.login

import android.os.Bundle
import com.example.izbela.R
import com.example.izbela.presentation.auth.AuthProtocol
import com.example.izbela.presentation.base.BaseMvpFragment
import com.jakewharton.rxbinding3.widget.textChanges
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_login.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LoginFragment : BaseMvpFragment<LoginPresenter, AuthProtocol.IAuthView>(),
    AuthProtocol.IAuthView {

    @Inject
    override lateinit var daggerPresenter: Lazy<LoginPresenter>

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    override fun providePresenter(): LoginPresenter {
        return daggerPresenter.get()
    }

    override val layoutId: Int = R.layout.fragment_login

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.listenFields(loginEt.textChanges(), passwordEt.textChanges())

        loginBtn.setOnClickListener {
            presenter.action(
                loginEt.text?.toString() ?: "",
                passwordEt.text?.toString() ?: ""
            )
        }

        registerBtn.setOnClickListener {
            presenter.registration()
        }
    }


    override fun failed() {
        showSnackBar("Неверный логин или пароль", registerBtn)
    }

    override fun validateFields(enabled: Boolean) {
        loginBtn.isEnabled = enabled
    }

}