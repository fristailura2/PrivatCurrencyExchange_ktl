package com.example.ura.myapplication.data.exchange.db

import android.arch.persistence.room.TypeConverter
import java.util.*


class DataConverter {
    @TypeConverter
    fun convert(time: Long): Date {
        return Date(time)
    }

    @TypeConverter
    fun convert(date: Date): Long {
        return date.time
    }
}
