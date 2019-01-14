package com.example.ura.myapplication.data.exchange.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import com.example.ura.myapplication.data.exchange.api.PrivateApiResponse


@Entity
class CurrencySave {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var date: String? = null
    var baseCurrencyLit: String? = null

    constructor(privateApiResponse: PrivateApiResponse) {
        this.date = privateApiResponse.date
        this.baseCurrencyLit = privateApiResponse.baseCurrencyLit
    }

    constructor() {}
}
