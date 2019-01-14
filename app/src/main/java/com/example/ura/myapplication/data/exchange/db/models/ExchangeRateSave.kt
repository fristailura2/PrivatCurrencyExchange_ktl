package com.example.ura.myapplication.data.exchange.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.example.ura.myapplication.data.exchange.api.ExchangeRate


@Entity(
    foreignKeys = [ForeignKey(
        entity = CurrencySave::class,
        childColumns = arrayOf("currencySaveId"),
        parentColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )], indices = [Index(value = "currencySaveId")]
)
class ExchangeRateSave : ExchangeRate {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var currencySaveId: Long = 0

    constructor()

    constructor(other: ExchangeRate) : super(other) {}

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is ExchangeRateSave) return false

        val that = o as ExchangeRateSave?

        return if (id != that!!.id) false else currencySaveId == that.currencySaveId
    }

    override fun hashCode(): Int {
        var result = (id xor id.ushr(32)).toInt()
        result = 31 * result + (currencySaveId xor currencySaveId.ushr(32)).toInt()
        return result
    }
}
