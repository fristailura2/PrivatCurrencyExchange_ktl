package com.example.ura.myapplication.presentation.graph

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.graphics.Paint
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.example.ura.myapplication.R
import com.example.ura.myapplication.presentation.base.BaseActivity
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.*
import javax.inject.Inject

class GraphActivity : BaseActivity<GraphPresenter>(), GraphView {
    override val getLayout: Int
        get() = R.layout.activity_graph
    override lateinit var presenter: GraphPresenter
        @Inject
        set

    @BindView(R.id.activity_graph_graphView)
    lateinit var graphView: com.jjoe64.graphview.GraphView
    @BindView(R.id.activity_graph_from_date_textView)
    lateinit var fromDateTextView: TextView
    @BindView(R.id.layout_nbu_item_date_textView)
    lateinit var toDateTextView: TextView
    private var lineGraphSeries: LineGraphSeries<GraphPoint>? = null

    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGraphView()
    }

    @OnClick(R.id.view2)
    fun onPickDateFromButtonClick() {
        presenter.onPickDateFromButtonClick()
    }

    @OnClick(R.id.layout_nbu_item_date_picker_button_appCompatButton)
    fun onPickDateToButtonClick() {
        presenter.onPickDateToButtonClick()
    }

    fun showDataPickerDialog(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this, listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun showDataPickerDialogFrom() {
        showDataPickerDialog(DatePickerDialog.OnDateSetListener{ _, year, monthOfYear, dayOfMonth ->
            presenter.onDateFromPicked(
                year,
                monthOfYear,
                dayOfMonth
            )
        })
    }

    override fun showDataPickerDialogTo() {
        showDataPickerDialog(DatePickerDialog.OnDateSetListener{ _, year, monthOfYear, dayOfMonth ->
            presenter.onDateToPicked(
                year,
                monthOfYear,
                dayOfMonth
            )
        })
    }

    override fun showProgressDialog(total: Int) {
        dialog = ProgressDialog(this)
        dialog!!.setTitle(resources.getString(R.string.loading_title))
        dialog!!.setMessage(resources.getString(R.string.loading_text))
        dialog!!.setCancelable(true)
        dialog!!.setOnCancelListener { dialogInterface ->
            dialog = null
            presenter!!.progressDialogCanceled()
        }
        dialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog!!.max = total
        dialog!!.show()
    }

    override fun incProgressDialog() {
        dialog!!.progress = dialog!!.progress + 1
    }

    override fun hideProgressDialog() {
        if (dialog != null)
            dialog!!.cancel()
        dialog = null
    }

    fun initGraphView() {
        lineGraphSeries = LineGraphSeries()
        graphView.addSeries(lineGraphSeries!!)

        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.isYAxisBoundsManual = true

        graphView.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(this)
        graphView.gridLabelRenderer.numHorizontalLabels = 4

        graphView.gridLabelRenderer.setHumanRounding(true)
        graphView.gridLabelRenderer.labelsSpace = 0
        graphView.gridLabelRenderer.verticalLabelsAlign = Paint.Align.LEFT
        graphView.gridLabelRenderer.verticalLabelsVAlign = GridLabelRenderer.VerticalLabelsVAlign.ABOVE
        graphView.gridLabelRenderer.setHorizontalLabelsAngle(30)
    }

    override fun addGraphPoint(graphPoint: GraphPoint) {
        lineGraphSeries!!.appendData(graphPoint, true, MAX_PINTS_FOR_GRAPH)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val graphPointIterator = lineGraphSeries!!.getValues(0.0, MAX_PINTS_FOR_GRAPH.toDouble())
        val serialData = ArrayList<GraphPoint>()
        while (graphPointIterator.hasNext())
            serialData.add(graphPointIterator.next())

        outState.putBoolean(LOADING_KEY, dialog == null)
        outState.putString(FROM_DATE_KEY, fromDateTextView.text.toString())
        outState.putString(TO_DATE_KEY, toDateTextView.text.toString())
        outState.putSerializable(GRAPH_DATA_KEY, serialData)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val loading = savedInstanceState.getBoolean(LOADING_KEY)
        val fromDate = savedInstanceState.getString(FROM_DATE_KEY)
        val toDate = savedInstanceState.getString(TO_DATE_KEY)
        val graphData = savedInstanceState.getSerializable(GRAPH_DATA_KEY) as List<GraphPoint>
        presenter.onOrientationChanged(loading, fromDate!!, toDate!!, graphData)
    }

    override fun addGraphPoint(graphPoints: List<GraphPoint>) {
        if (graphPoints.isEmpty())
            return

        val lowest = graphPoints[0]
        val heist = graphPoints[graphPoints.size - 1]

        lineGraphSeries!!.resetData(graphPoints.toTypedArray())

        graphView.viewport.setMinX(lowest.x)
        graphView.viewport.setMaxX(heist.x)


        val minMax=Pair(graphPoints.maxBy { it.y }!!,graphPoints.minBy { it.y }!!)

        val delta = (minMax.second.y - minMax.first.y) / 5
        graphView.viewport.setMaxY(minMax.second.y + delta)
        graphView.viewport.setMinY(minMax.first.y - delta)
    }

    override fun setFromDateText(from: String) {
        fromDateTextView.text = from
    }

    override fun setToDateText(to: String) {
        toDateTextView.text = to
    }

    companion object {
        private const val FROM_DATE_KEY = "from_key"
        private const val TO_DATE_KEY = "to_key"
        private const val LOADING_KEY = "loading_key"
        private const val GRAPH_DATA_KEY = "graph_data_key"
        private const val MAX_PINTS_FOR_GRAPH = 1500
    }


}
