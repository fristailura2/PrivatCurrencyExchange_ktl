package com.example.ura.myapplication.data.exchange

import com.example.ura.myapplication.data.exchange.api.PrivateApiResponse
import com.example.ura.myapplication.data.exchange.api.PrivateExchangeApi
import com.example.ura.myapplication.data.exchange.db.CurrencyDao
import com.example.ura.myapplication.data.exchange.db.models.CurrencySave
import com.example.ura.myapplication.data.exchange.db.models.CurrencyWithExchangeRate
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.utils.Optional
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

class ExchangeRepositoryImpl(private val currencyDao: CurrencyDao, private val currencyApi: PrivateExchangeApi) :
    ExchengeRepository {

    override fun getCurrencyWithExchangeRate(date: String): Single<CurrencyWithExchangeRate> {
        return getCurrencyWithExchangeRate(Arrays.asList(date)).firstOrError()
    }

    override fun getCurrencyWithExchangeRate(dates: List<String>): Observable<CurrencyWithExchangeRate> {
        val requests = ArrayList<Single<CurrencyWithExchangeRate>>()

        for (currentRequestDate in dates) {
            val currentRequest = Single.fromCallable { Optional.from(currencyDao.getExchangeRate(currentRequestDate)) }
                .flatMap {
                    if (it.isNull)
                        currencyApi.getPrivateApi(currentRequestDate)
                            .map<CurrencyWithExchangeRate> { this.convert(it) }
                            .doOnSuccess { this.insertIfNotEmpty(it) }
                    else
                        Single.just(it.get()!!)
                }
            requests.add(currentRequest)
        }
        return Single.merge(requests).toObservable()
    }

    private fun insertIfNotEmpty(currencyWithExchangeRate: CurrencyWithExchangeRate) {
        if (!currencyWithExchangeRate.exchangeRateSaves!!.isEmpty())
            currencyDao.insert(currencyWithExchangeRate)
    }

    private fun convert(privateApiResponse: PrivateApiResponse): CurrencyWithExchangeRate {
        val exchangeRateSaveList = ArrayList<ExchangeRateSave>()
        privateApiResponse.exchangeRate!!.forEach { if (it.currency != null) exchangeRateSaveList.add(ExchangeRateSave(it))}
        return CurrencyWithExchangeRate(CurrencySave(privateApiResponse), exchangeRateSaveList)
    }
}
