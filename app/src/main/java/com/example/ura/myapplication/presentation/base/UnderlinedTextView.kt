package com.example.ura.myapplication.presentation.base

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet

class UnderlinedTextView : android.support.v7.widget.AppCompatTextView {

    constructor(context: Context) : super(context) {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}
