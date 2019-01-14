package com.example.ura.myapplication.presentation.main


import com.example.ura.myapplication.di.annotaion.ActivityScope
import com.example.ura.myapplication.presentation.base.BasePresenter
import javax.inject.Inject

@ActivityScope
class MainPresenter @Inject
constructor() : BasePresenter<MainView>() {

    fun onMenuGraphButtonClick() {
        view.openGraphActivity()
    }
}
