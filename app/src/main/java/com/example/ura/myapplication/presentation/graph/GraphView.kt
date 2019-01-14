package com.example.ura.myapplication.presentation.graph

import com.example.ura.myapplication.presentation.base.View

interface GraphView : View {
    fun showDataPickerDialogFrom()

    fun showDataPickerDialogTo()

    fun showProgressDialog(total: Int)

    fun incProgressDialog()

    fun hideProgressDialog()

    fun addGraphPoint(graphPoint: GraphPoint)

    fun addGraphPoint(graphPoint: List<GraphPoint>)

    fun setFromDateText(from: String)

    fun setToDateText(to: String)
}
