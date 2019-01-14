package com.example.ura.myapplication.presentation.main


import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import io.reactivex.Observable

interface BankItemClickObserverReceiver {
    fun onObservableReceive(observable: Observable<ExchangeRateSave>)
}
