package com.example.ura.myapplication.presentation.base.bank

import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.presentation.base.View

interface BankView : View {
    fun setExchangeRateSaves(exchangeRateSaves: MutableList<ExchangeRateSave>)

    fun showDataPickerDialog()

    fun showDate(date: String)
}
