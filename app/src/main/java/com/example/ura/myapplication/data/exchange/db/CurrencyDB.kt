package com.example.ura.myapplication.data.exchange.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import com.example.ura.myapplication.data.exchange.db.models.CurrencySave
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave

@TypeConverters(DataConverter::class)
@Database(version = 8, entities = arrayOf(CurrencySave::class, ExchangeRateSave::class))
abstract class CurrencyDB : RoomDatabase() {
    abstract val currencyDao: CurrencyDao
}
