package com.example.ura.myapplication.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    private var dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    @Throws(ParseException::class)
    fun getYear(stringDate: String): Int {
        return get(stringDate, Calendar.YEAR)
    }

    @Throws(ParseException::class)
    fun getDayOfMonth(stringDate: String): Int {
        return get(stringDate, Calendar.DAY_OF_MONTH)
    }

    @Throws(ParseException::class)
    fun getMonth(stringDate: String): Int {
        return get(stringDate, Calendar.MONTH)
    }

    fun getStringDate(year: Int, month: Int, dayOfMonth: Int): String {
        return dateFormat.format(getDate(year, month, dayOfMonth))
    }

    @Throws(ParseException::class)
    private operator fun get(stringDate: String, dateElement: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(stringDate)
        return calendar.get(dateElement)
    }

    fun getStringDate(dateFrom: Date): String {
        return dateFormat.format(dateFrom)
    }

    fun getDate(year: Int, month: Int, dayOfMonth: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return calendar.time
    }

    @Throws(ParseException::class)
    fun getDate(stringDate: String): Date {
        return dateFormat.parse(stringDate)
    }

    fun getDaysForRange(form: Date, to: Date): List<Date> {
        val dayInMilles = TimeUnit.DAYS.toMillis(1)

        val res = ArrayList<Date>()

        var date = form
        while (date.before(to)) {
            res.add(date)
            date = Date(date.time + dayInMilles)
        }

        return res
    }

    fun getDaysForRangeAsString(form: Date, to: Date): List<String> {
        val dates = getDaysForRange(form, to)
        val res = ArrayList<String>(dates.size)
        dates.forEach { res.add(dateFormat.format(it)) }

        return res
    }


}
