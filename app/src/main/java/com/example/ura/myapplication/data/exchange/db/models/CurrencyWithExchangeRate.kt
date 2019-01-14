package com.example.ura.myapplication.data.exchange.db.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class CurrencyWithExchangeRate {
    @Embedded
    var currencySave: CurrencySave? = null
    @Relation(parentColumn = "id", entityColumn = "currencySaveId", entity = ExchangeRateSave::class)
    var exchangeRateSaves: List<ExchangeRateSave>? = null

    constructor() {}

    constructor(currencySave: CurrencySave, exchangeRateSaves: List<ExchangeRateSave>) {
        this.currencySave = currencySave
        this.exchangeRateSaves = exchangeRateSaves
    }

    fun findExchangeRateSaveByCurrency(currency: String): ExchangeRateSave? {
        for (exchangeRateSave in exchangeRateSaves!!) {
            if (exchangeRateSave.currency == currency)
                return exchangeRateSave
        }
        return null
    }
}
