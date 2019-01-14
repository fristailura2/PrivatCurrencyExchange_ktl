package com.example.ura.myapplication.presentation.main.nbu


import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.presentation.base.bank.BankView

interface NBUView : BankView {
    fun scrollTo(exchangeRateSave: ExchangeRateSave)
}
