package com.x.presentation.ui.composable

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.EntryXComparator
import com.x.presentation.R
import java.util.*

enum class ChartType(val value: Int) {
    BAR(R.string.chart_type_bar_text),
    LINE(R.string.chart_type_line_text);

    companion object {
        fun get(value: Int): ChartType = values().first { it.value == value }
    }
}

@Composable
fun Chart(
    isChartLine: Boolean,
    x: List<Float>,
    y: List<Float>,
) {
    AndroidView(
        factory = { context ->
            if (isChartLine)
                barChartView(context, x, y)
            else
                lineChartView(context, x, y)
        },
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(200.dp)
    )
}

fun barChartView(context: Context, x: List<Float>, y: List<Float>): BarChart {
    return BarChart(context).apply {
        description.isEnabled = false
        isDragEnabled = false
        xAxis.isEnabled = false
        axisLeft.textColor = ContextCompat.getColor(context, R.color.colorChartPrimary)
        axisLeft.setDrawGridLines(false)
        xAxis.setDrawGridLines(false)
        axisLeft.setDrawAxisLine(false)
        axisRight.isEnabled = false
        legend.isEnabled = false

        val entry = ArrayList<BarEntry>()
        x.forEachIndexed { index, _ ->
            entry.add(BarEntry(x[index], y[index]))
        }

        Collections.sort(entry, EntryXComparator())

        setPadding(0, 0, 0, 0)
        setTouchEnabled(false)
        setScaleEnabled(false)
        setDrawGridBackground(false)
        setDrawBorders(false)
        invalidate()

        val colorPrimary = ContextCompat.getColor(context, R.color.colorChartPrimary)
        val colorAlpha = ContextCompat.getColor(context, R.color.colorChartPrimaryAlpha)

        val lines = BarDataSet(entry, "buildCount").apply {
            color = colorPrimary
            highLightColor = ContextCompat.getColor(context, R.color.colorChartPrimaryAlpha)
        }

        lines.setGradientColor(colorAlpha, colorPrimary)

        clear()
        data = BarData(lines).apply {
            setDrawValues(false)
        }

        animateX(300)
    }
}

fun lineChartView(context: Context, x: List<Float>, y: List<Float>): LineChart {
    return LineChart(context).apply {
        description.isEnabled = false
        isDragEnabled = false
        xAxis.isEnabled = false
        axisLeft.textColor = ContextCompat.getColor(context, R.color.colorChartPrimary)
        axisLeft.setDrawGridLines(false)
        xAxis.setDrawGridLines(false)
        axisLeft.setDrawAxisLine(false)
        axisRight.isEnabled = false
        legend.isEnabled = false

        val entry = ArrayList<Entry>()
        x.forEachIndexed { index, _ ->
            entry.add(Entry(x[index], y[index]))
        }

        Collections.sort(entry, EntryXComparator())

        setPadding(0, 0, 0, 0)
        setTouchEnabled(false)
        setScaleEnabled(false)
        setDrawGridBackground(false)
        setDrawBorders(false)
        invalidate()

        val lines = LineDataSet(entry, "buildCount").apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = ContextCompat.getColor(context, R.color.colorChartPrimary)
            highLightColor = ContextCompat.getColor(context, R.color.colorChartPrimary)
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.bg_chart)
            lineWidth = 3f
            setDrawFilled(true)
            setDrawCircles(false)
        }

        clear()
        data = LineData(lines).apply {
            setDrawValues(false)
        }

        animateX(300)
    }
}