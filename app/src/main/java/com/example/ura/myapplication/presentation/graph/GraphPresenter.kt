package com.example.ura.myapplication.presentation.graph

import com.example.ura.myapplication.data.exchange.ExchengeRepository
import com.example.ura.myapplication.di.annotaion.ActivityScope
import com.example.ura.myapplication.presentation.base.BasePresenter
import com.example.ura.myapplication.utils.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.ParseException
import java.util.*
import javax.inject.Inject

@ActivityScope
class GraphPresenter @Inject
constructor(private val exchengeRepository: ExchengeRepository) : BasePresenter<GraphView>() {
    private var dateFrom: Date? = null
    private var dateTo: Date? = null
    private var dateObservableDisposable: Disposable? = null

    fun onPickDateFromButtonClick() {
        view.showDataPickerDialogFrom()
    }

    fun onPickDateToButtonClick() {
        view.showDataPickerDialogTo()
    }

    fun onDateFromPicked(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dateFrom = DateUtils.getDate(year, monthOfYear, dayOfMonth)
        view.setFromDateText(DateUtils.getStringDate(dateFrom!!))
        loadDateIfNotNull()
    }

    fun onDateToPicked(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dateTo = DateUtils.getDate(year, monthOfYear, dayOfMonth)
        view.setToDateText(DateUtils.getStringDate(dateTo!!))
        loadDateIfNotNull()
    }

    override fun onStop() {
        super.onStop()
        if (dateObservableDisposable != null)
            dateObservableDisposable!!.dispose()
        dateObservableDisposable = null
    }

    private fun loadDateIfNotNull() {
        if (dateFrom != null && dateTo != null) {
            if (dateFrom!!.time > dateTo!!.time) {
                view.showText("Incorrect date range from cant be after to")
                return
            }
            if (Date().before(dateTo)) {
                view.showText("Date should be before now")
                return
            }
            val dates = DateUtils.getDaysForRangeAsString(dateFrom!!, dateTo!!)
            view.showProgressDialog(dates.size)
            dateObservableDisposable = exchengeRepository.getCurrencyWithExchangeRate(dates)
                .subscribeOn(Schedulers.io())
                .filter { currencyWithExchangeRate -> !currencyWithExchangeRate.exchangeRateSaves!!.isEmpty() }
                .map { currencyWithExchangeRate ->
                    GraphPoint(
                        DateUtils.getDate(currencyWithExchangeRate.currencySave!!.date!!).time.toDouble(),
                        currencyWithExchangeRate.findExchangeRateSaveByCurrency("USD")!!.purchaseRateNB
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { view.incProgressDialog() }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { view.hideProgressDialog() }
                .subscribe { graphPoint -> view.addGraphPoint(graphPoint) }
        }
    }


    fun progressDialogCanceled() {
        dateObservableDisposable!!.dispose()
        dateObservableDisposable = null
    }

    fun onOrientationChanged(loading: Boolean, fromDate: String, toDate: String, graphData: List<GraphPoint>) {
        try {
            dateFrom = DateUtils.getDate(fromDate)
            dateTo = DateUtils.getDate(toDate)
            view.setFromDateText(fromDate)
            view.setToDateText(toDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (loading)
            loadDateIfNotNull()
        if (!graphData.isEmpty())
            view.addGraphPoint(graphData)
    }
}
