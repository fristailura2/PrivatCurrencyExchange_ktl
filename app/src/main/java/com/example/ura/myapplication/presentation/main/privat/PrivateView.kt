package com.example.ura.myapplication.presentation.main.privat


import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.presentation.base.bank.BankView
import io.reactivex.Observable

interface PrivateView : BankView {
    val clickObservable: Observable<ExchangeRateSave>
    fun sendClickObservableUp()
}
