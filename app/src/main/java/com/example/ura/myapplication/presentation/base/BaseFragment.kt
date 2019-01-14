package com.example.ura.myapplication.presentation.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import butterknife.ButterKnife
import dagger.android.support.DaggerFragment


abstract class BaseFragment<T : BasePresenter<*>> : DaggerFragment(), View {
    protected open lateinit var presenter: T

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this,view)
    }

    override fun showText(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG)
            .show()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter.bind(this)
    }
    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}