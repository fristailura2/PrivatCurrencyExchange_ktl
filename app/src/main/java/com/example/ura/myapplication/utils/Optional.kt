package com.example.ura.myapplication.utils

class Optional<T>(private var `val`: T?) {
    val isNull: Boolean
        get() = `val` == null

    fun get(): T? {
        return `val`
    }

    fun set(`val`: T) {
        this.`val` = `val`
    }

    companion object {
        fun <V> from(`val`: V): Optional<V> {
            return Optional(`val`)
        }
    }
}
