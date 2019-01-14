package com.example.ura.myapplication.utils

import android.content.res.Resources
import com.example.ura.myapplication.R

object CurrencyCodeUtils {
    fun getNameByCode(resources: Resources, code: String): String? {
        val keyValArray = resources.getStringArray(R.array.key_val_currency_code)
        val keyVal = keyValArray[keyValArray.takeWhile { it.split(",")[0] != code }.size]

        return keyVal.split(",")[1]
    }
}
