package com.example.izbela.presentation.auth.registration

import android.os.Bundle
import com.example.izbela.R
import com.example.izbela.presentation.auth.AuthProtocol
import com.example.izbela.presentation.base.BaseMvpFragment
import com.jakewharton.rxbinding3.widget.textChanges
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_reg.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class RegistrationFragment : BaseMvpFragment<RegistrationPresenter, AuthProtocol.IAuthView>(),
    AuthProtocol.IAuthView {

    override fun failed() {
    }

    override fun validateFields(enabled: Boolean) {
        registerBtn.isEnabled = enabled
    }

    @Inject
    override lateinit var daggerPresenter: Lazy<RegistrationPresenter>

    @InjectPresenter
    lateinit var presenter: RegistrationPresenter

    @ProvidePresenter
    override fun providePresenter(): RegistrationPresenter {
        return daggerPresenter.get()
    }

    override val layoutId: Int = R.layout.fragment_reg

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.listenFields(loginEt.textChanges(), passwordEt.textChanges())

        registerBtn.setOnClickListener {
            presenter.action(
                loginEt.text?.toString() ?: "",
                passwordEt.text?.toString() ?: ""
            )
        }
    }

}