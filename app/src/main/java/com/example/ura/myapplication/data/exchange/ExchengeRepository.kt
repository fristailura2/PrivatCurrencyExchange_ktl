package com.example.ura.myapplication.data.exchange

import com.example.ura.myapplication.data.exchange.db.models.CurrencyWithExchangeRate

import io.reactivex.Observable
import io.reactivex.Single

interface ExchengeRepository {
    fun getCurrencyWithExchangeRate(date: String): Single<CurrencyWithExchangeRate>

    fun getCurrencyWithExchangeRate(dates: List<String>): Observable<CurrencyWithExchangeRate>
}
