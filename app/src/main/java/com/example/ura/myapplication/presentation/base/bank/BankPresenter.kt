package com.example.ura.myapplication.presentation.base.bank

import com.example.ura.myapplication.data.exchange.ExchengeRepository
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.presentation.base.BasePresenter
import com.example.ura.myapplication.utils.DateUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

abstract class BankPresenter<T : BankView>(protected var exchengeRepository: ExchengeRepository) : BasePresenter<T>() {
    protected var compositeDisposable = CompositeDisposable()
    protected var dataDisposable: Disposable? = null
    var filter =Predicate<ExchangeRateSave> { true }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    fun onDataPicked(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        disposeCurrencyWithExchangeRate()

        val formatedDate = DateUtils.getStringDate(year, monthOfYear, dayOfMonth)

        view.showDate(formatedDate)
        compositeDisposable.add(exchengeRepository.getCurrencyWithExchangeRate(formatedDate)
            .subscribeOn(Schedulers.io())
            .map<List<ExchangeRateSave>> { it.exchangeRateSaves }
            .flatMapObservable { exchangeRateSaves -> Observable.fromArray(*exchangeRateSaves.toTypedArray()) }
            .filter(filter)
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                if (it.isEmpty())
                    view.showText("No data for $formatedDate")
            }
            .subscribe(
                { exchangeRateSaves -> view.setExchangeRateSaves(exchangeRateSaves) },
                { view.showText("Some problem during getting data from api.privatbank.ua") })
        )
    }

    private fun disposeCurrencyWithExchangeRate() {
        if (dataDisposable != null)
            dataDisposable!!.dispose()
        dataDisposable = null
    }

    fun onPickDateButtonClick() {
        view.showDataPickerDialog()
    }

    fun onOrientationChanged(date: String) {
        try {
            if ("" != date)
                onDataPicked(DateUtils.getYear(date), DateUtils.getMonth(date), DateUtils.getDayOfMonth(date))
        } catch (e: Exception) {}

    }


}
