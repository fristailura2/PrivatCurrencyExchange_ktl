package com.example.ura.myapplication.data.exchange

import android.arch.persistence.room.Room
import android.content.Context
import com.example.ura.myapplication.data.exchange.api.PrivateExchangeApi
import com.example.ura.myapplication.data.exchange.db.CurrencyDB
import com.example.ura.myapplication.data.exchange.db.CurrencyDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExchengeRepositoryModule {
    @Provides
    @Singleton
    fun provideExchengeRepository(
        currencyDao: CurrencyDao,
        privateExchangeApi: PrivateExchangeApi
    ): ExchengeRepository {
        return ExchangeRepositoryImpl(currencyDao, privateExchangeApi)
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(currencyDB: CurrencyDB): CurrencyDao {
        return currencyDB.currencyDao
    }

    @Provides
    @Singleton
    fun provideCurrencyDB(context: Context): CurrencyDB {
        return Room.databaseBuilder(context, CurrencyDB::class.java, CurrencyDB::class.java.simpleName)
            .fallbackToDestructiveMigration()
            .build()
    }
}
