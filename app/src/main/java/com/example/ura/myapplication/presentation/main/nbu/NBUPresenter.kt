package com.example.ura.myapplication.presentation.main.nbu

import com.example.ura.myapplication.data.exchange.ExchengeRepository
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.di.annotaion.FragmentScope
import com.example.ura.myapplication.presentation.base.bank.BankPresenter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class NBUPresenter @Inject
constructor(exchengeRepository: ExchengeRepository) : BankPresenter<NBUView>(exchengeRepository) {
    private var privateItemClickDisposable: Disposable? = null

    fun onPrivateClickObservableReceived(observable: Observable<ExchangeRateSave>) {
        if (privateItemClickDisposable != null) {
            privateItemClickDisposable!!.dispose()
            privateItemClickDisposable = null
        }
        privateItemClickDisposable = observable.subscribe { exchangeRateSave -> view!!.scrollTo(exchangeRateSave) }
        compositeDisposable.add(privateItemClickDisposable!!)
    }

}
