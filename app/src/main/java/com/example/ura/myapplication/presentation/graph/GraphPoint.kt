package com.example.ura.myapplication.presentation.graph

import com.jjoe64.graphview.series.DataPoint
import java.io.Serializable

class GraphPoint(xs:Double,ys:Double) : DataPoint(xs,ys), Serializable
