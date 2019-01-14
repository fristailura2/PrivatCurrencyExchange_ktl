package com.example.ura.myapplication.data.exchange.api

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class PrivateApiResponse : Serializable {
    @SerializedName("date")
    var date: String? = null
    @SerializedName("bank")
    var bank: String? = null
    @SerializedName("baseCurrency")
    var baseCurrency: Int = 0
    @SerializedName("baseCurrencyLit")
    var baseCurrencyLit: String? = null
    @SerializedName("exchangeRate")
    var exchangeRate: List<ExchangeRate>? = null

    companion object {
        private const val serialVersionUID = 8158297174769096074L
    }


}