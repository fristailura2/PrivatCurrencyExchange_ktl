package com.example.ura.myapplication.presentation.main.privat

import com.example.ura.myapplication.data.exchange.ExchengeRepository
import com.example.ura.myapplication.di.annotaion.FragmentScope
import com.example.ura.myapplication.presentation.base.bank.BankPresenter
import io.reactivex.functions.Predicate

import javax.inject.Inject

@FragmentScope
class PrivatePresenter @Inject
constructor(exchengeRepository: ExchengeRepository) : BankPresenter<PrivateView>(exchengeRepository) {
    init {
        filter= Predicate{ exchangeRateSave ->
            Math.abs(exchangeRateSave.purchaseRate)<0.0000001
        }
    }

    override fun onStart() {
        super.onStart()
        view.sendClickObservableUp()
    }

}
