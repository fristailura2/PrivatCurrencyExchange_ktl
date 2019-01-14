package com.example.ura.myapplication.presentation.base

open class BasePresenter<T : View> {
    fun bind(bind: View) {
        this.view= bind as T
    }

    protected lateinit var view: T

    open fun onStop(){}
    open fun onStart(){}
}