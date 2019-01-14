package com.example.ura.myapplication.data.exchange.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.example.ura.myapplication.data.exchange.db.models.CurrencySave
import com.example.ura.myapplication.data.exchange.db.models.CurrencyWithExchangeRate
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave

@Dao
abstract class CurrencyDao {
    @Transaction
    @Query("SELECT * FROM CurrencySave WHERE date=:date")
    abstract fun getExchangeRate(date: String): CurrencyWithExchangeRate

    @Insert
    protected abstract fun insert(currencySave: CurrencySave): Long

    @Insert
    protected abstract fun insert(currencySave: List<ExchangeRateSave>)

    @Transaction
    open fun insert(currencyWithExchangeRate: CurrencyWithExchangeRate) {
        val id = insert(currencyWithExchangeRate.currencySave!!)
        for (exchangeRateSave in currencyWithExchangeRate.exchangeRateSaves!!)
            exchangeRateSave.currencySaveId = id
        insert(currencyWithExchangeRate.exchangeRateSaves!!)
    }

}
