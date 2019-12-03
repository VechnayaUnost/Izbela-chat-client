package com.example.izbela.customview

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.izbela.R

class ProgressDialog(
    context: Context,
    private val onBackPressedListener: (() -> Unit)? = null
) :
    Dialog(context, R.style.FullScreenDialog) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        setContentView(view)
    }

    override fun onBackPressed() {
        /*super.onBackPressed()*/
        onBackPressedListener?.invoke()
    }
}