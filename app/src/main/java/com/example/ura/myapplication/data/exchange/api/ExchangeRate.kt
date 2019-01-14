package com.example.ura.myapplication.data.exchange.api

import com.google.gson.annotations.SerializedName

import java.io.Serializable

open class ExchangeRate : Serializable {
    @SerializedName("baseCurrency")
    var baseCurrency: String? = null
    @SerializedName("currency")
    var currency: String? = null
    @SerializedName("saleRateNB")
    var saleRateNB: Double = 0.toDouble()
    @SerializedName("purchaseRateNB")
    var purchaseRateNB: Double = 0.toDouble()
    @SerializedName("saleRate")
    var saleRate: Double = 0.toDouble()
    @SerializedName("purchaseRate")
    var purchaseRate: Double = 0.toDouble()

    constructor(other: ExchangeRate) {
        this.baseCurrency = other.baseCurrency
        this.currency = other.currency
        this.saleRateNB = other.saleRateNB
        this.purchaseRateNB = other.purchaseRateNB
        this.saleRate = other.saleRate
        this.purchaseRate = other.purchaseRate
    }

    constructor() {}

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is ExchangeRate) return false

        val that = o as ExchangeRate?

        if (java.lang.Double.compare(that!!.saleRateNB, saleRateNB) != 0) return false
        if (java.lang.Double.compare(that.purchaseRateNB, purchaseRateNB) != 0) return false
        if (java.lang.Double.compare(that.saleRate, saleRate) != 0) return false
        if (java.lang.Double.compare(that.purchaseRate, purchaseRate) != 0) return false
        if (if (baseCurrency != null) baseCurrency != that.baseCurrency else that.baseCurrency != null)
            return false
        return if (currency != null) currency == that.currency else that.currency == null
    }

    override fun hashCode(): Int {
        var result: Int
        var temp: Long
        result = if (baseCurrency != null) baseCurrency!!.hashCode() else 0
        result = 31 * result + if (currency != null) currency!!.hashCode() else 0
        temp = java.lang.Double.doubleToLongBits(saleRateNB)
        result = 31 * result + (temp xor temp.ushr(32)).toInt()
        temp = java.lang.Double.doubleToLongBits(purchaseRateNB)
        result = 31 * result + (temp xor temp.ushr(32)).toInt()
        temp = java.lang.Double.doubleToLongBits(saleRate)
        result = 31 * result + (temp xor temp.ushr(32)).toInt()
        temp = java.lang.Double.doubleToLongBits(purchaseRate)
        result = 31 * result + (temp xor temp.ushr(32)).toInt()
        return result
    }

    companion object {
        private const val serialVersionUID = -7604114760294605421L
    }
}