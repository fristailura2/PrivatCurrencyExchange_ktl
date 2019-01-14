package com.example.ura.myapplication.data.exchange.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PrivateExchangeApi {
    @GET("p24api/exchange_rates?json")
    fun getPrivateApi(@Query("date") date: String): Single<PrivateApiResponse>
}
