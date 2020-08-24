package com.hmsg.hospitalreport.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.hmsg.hospitalreport.R
import com.hmsg.hospitalreport.adapter.ExcelPanelAdapter
import com.hmsg.hospitalreport.bean.Cell
import com.hmsg.hospitalreport.chart.LineChartMarkView2
import com.hmsg.hospitalreport.chart.linechart.ILineChartData
import com.hmsg.hospitalreport.chart.linechart.LineChartHelper
import com.hmsg.hospitalreport.utils.TimeSelectView
import com.hmsg.hospitalreport.utils.TimeUtlis
import com.hmsg.hospitalreport.utils.immersiveDark
import com.hmsg.hospitalreport.viewmodel.DialogueReportViewModel
import kotlinx.android.synthetic.main.store_sales_act_layout.*
import kotlinx.android.synthetic.main.include_titlebar_layout.*
import kotlinx.android.synthetic.main.store_sales_act_layout.excelPanel
import kotlinx.android.synthetic.main.store_sales_act_layout.line_chart
import kotlinx.android.synthetic.main.store_sales_act_layout.time_select

/**
 * 门店销售报表
 */
class StoreSalesActivity : AppCompatActivity(R.layout.store_sales_act_layout), TimeSelectView.TimeSelectInterFace{
    private val requestBannerViewModel : DialogueReportViewModel by viewModels()
    lateinit var excelAdapter : ExcelPanelAdapter
    var names : MutableList<String> = mutableListOf()
    var timeSelectList = mutableListOf("本周","上周","本月")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersiveDark(titleBar)
        with(intent){
            titleBar_title_tv.setText(getStringExtra("title"))
        }
        back_iv.setOnClickListener { onBackPressed() }
        time_select.setSelectInterFace(this)
        time_select.setDataList(timeSelectList)

        excelAdapter = ExcelPanelAdapter(this,blockListener,"日期")
        excelPanel.setAdapter(excelAdapter)

        names = mutableListOf("包裹数","订单金额","含税订单金额","商品种类","商品的销售数量","不含税订单金额","不含税成本金额","退货单数","含税退货金额")
        requestBannerViewModel.getSingleExcelData(names,TimeUtlis.getWeekDays1(0))
        requestBannerViewModel.run {
            if (!line_chart.isEmpty)line_chart.clear()
            if (!line_chart1.isEmpty)line_chart1.clear()
            excellineList.observe(this@StoreSalesActivity, Observer {
                excelAdapter.setAllData(it.data1,it.data,it.data2)
                //线图
                var drawables : Array<Drawable?> = arrayOf()
                var callDurationColors : IntArray = intArrayOf()

                drawables = arrayOf(this?.let { ContextCompat.getDrawable(this@StoreSalesActivity, R.drawable.linechart_color_bg1) })
                callDurationColors = intArrayOf(Color.parseColor("#547DF9"))

                setLineData(line_chart,it.data3,"包裹数",drawables,callDurationColors,6)
                setLineData(line_chart1,it.data4,"含税订单金额",drawables,callDurationColors,6)


            })
        }
    }

    private fun setLineData(lineChart: LineChart?, data3: MutableList<ILineChartData>, name: String,drawables : Array<Drawable?>,  callDurationColors : IntArray, count: Int) {
        var lineChartHelper = LineChartHelper(lineChart)
        lineChartHelper.isLegend = true
        lineChartHelper.legendSize = 9f
        var mv = LineChartMarkView2(this@StoreSalesActivity)
        mv.chartView = lineChart
        lineChart?.marker = mv
        lineChartHelper.showSingleLineChart(data3,name,callDurationColors[0], count)
        lineChartHelper.toggleFilled(drawables,callDurationColors,true)

    }

    override fun onSelectTimeChange(position: Int, data: String?) {
        when(data){
            "本周" ->{
                requestBannerViewModel.getSingleExcelData(names, TimeUtlis.getWeekDays1(0))
            }
            "上周" ->{
                requestBannerViewModel.getSingleExcelData(names, TimeUtlis.getWeekDays1(-1))
            }
            "本月" ->{
                requestBannerViewModel.getSingleExcelData(names,
                    TimeUtlis.getDays(TimeUtlis.getBeginDayOfMonth(), TimeUtlis.getEndDayOfMonth()))
            }
        }
    }

    val blockListener = View.OnClickListener {
        val cell = it.tag as Cell
        if (cell!=null){

        }
    }
}