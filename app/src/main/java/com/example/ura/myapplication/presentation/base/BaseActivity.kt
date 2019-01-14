package com.example.ura.myapplication.presentation.base

import android.os.Bundle

import android.widget.Toast
import butterknife.ButterKnife
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : BasePresenter<out View>> : DaggerAppCompatActivity(), View {
    abstract var presenter: T
    abstract val getLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout)
        ButterKnife.bind(this)
    }

    override fun showText(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG)
            .show()
    }
    override fun onStart() {
        super.onStart()
        presenter.bind(this)
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

}