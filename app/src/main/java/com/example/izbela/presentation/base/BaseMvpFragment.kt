package com.example.izbela.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.izbela.R
import com.example.izbela.customview.ProgressDialog
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import moxy.MvpAppCompatFragment
import moxy.MvpView
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseMvpFragment<P : BasePresenter<V>, V : MvpView> : MvpAppCompatFragment(),
    HasAndroidInjector {

    abstract val daggerPresenter: Lazy<P>

    abstract fun providePresenter(): P

    private var progressDialog: ProgressDialog? = null

    open var titleId: Int? = null

    var toolbar: Toolbar? = null

    abstract val layoutId: Int

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var cicerone: Cicerone<Router>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(layoutId, container, false)
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        progressDialog = ProgressDialog(context)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }


    fun showProgress(show: Boolean) {
        if (show) progressDialog?.show() else progressDialog?.dismiss()
    }

    fun showSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = setupToolbar(titleId, cicerone)
    }
}

fun Fragment.setupToolbar(
    titleId: Int?,
    cicerone: Cicerone<Router>?
): Toolbar? {

    val toolbar = view?.findViewById<Toolbar?>(R.id.toolbar)
    toolbar?.let {
        titleId?.let {
            toolbar.title = getString(titleId)
        }

    }

    toolbar?.setNavigationOnClickListener {
        cicerone?.router?.exit()
    }
    return toolbar
}

