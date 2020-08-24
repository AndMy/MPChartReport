package com.hmsg.hospitalreport.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.hmsg.hospitalreport.R
import com.hmsg.hospitalreport.adapter.ExcelPanelAdapter
import com.hmsg.hospitalreport.bean.Cell
import com.hmsg.hospitalreport.chart.LineChartMarkView
import com.hmsg.hospitalreport.chart.LineChartMarkView1
import com.hmsg.hospitalreport.chart.linechart.LineChartHelper
import com.hmsg.hospitalreport.utils.*
import com.hmsg.hospitalreport.viewmodel.DialogueReportViewModel
import kotlinx.android.synthetic.main.dialogue_report_act_layout.*
import kotlinx.android.synthetic.main.include_titlebar_layout.*

/*
* 医患对话报表
* */
class DialogueReportActivity : AppCompatActivity(),TimeSelectView.TimeSelectInterFace {
    private val requestBannerViewModel : DialogueReportViewModel by viewModels()
    lateinit var excelAdapter : ExcelPanelAdapter
    var names : MutableList<String> = mutableListOf()
    var status : String =""
    var timeSelectList = mutableListOf("本周","上周","本月")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialogue_report_act_layout)
        immersiveDark(titleBar)
        with(intent){
            titleBar_title_tv.setText(getStringExtra("title"))
            status = getStringExtra("status")
        }
        back_iv.setOnClickListener { onBackPressed() }
        time_select.setSelectInterFace(this)
        time_select.setDataList(timeSelectList)
        //time_select.setDefaultPosition(2)

        excelAdapter = ExcelPanelAdapter(this,blockListener,"日期")
        excelPanel.setAdapter(excelAdapter)
        if (status.isNotEmpty()&&status.equals("2")){
            names = mutableListOf("咨询患者数","回复医生数","推荐用药医生数","无回复医生数")
        }else{
            names = mutableListOf("总咨询条数","患者咨询数")
        }
        requestBannerViewModel.getExcelData(names,TimeUtlis.getWeekDays1(0))
        requestBannerViewModel.run {
            if (!line_chart.isEmpty)line_chart.clear()
            excelList.observe(this@DialogueReportActivity, Observer {
                excelAdapter.setAllData(it.data1,it.data,it.data2)
                //线图
                var drawables : Array<Drawable?> = arrayOf()
                var callDurationColors : IntArray = intArrayOf()
                when(names.size){
                    2 ->{
                        drawables = arrayOf(
                            this?.let { ContextCompat.getDrawable(this@DialogueReportActivity, R.drawable.linechart_color_bg) }
                            ,this?.let { ContextCompat.getDrawable(this@DialogueReportActivity, R.drawable.linechart_color_bg1) }
                        )
                        callDurationColors = intArrayOf(
                            Color.parseColor("#A4D882")
                            ,Color.parseColor("#547DF9")
                        )
                    }
                    4 ->{
                        drawables = arrayOf(
                            this?.let { ContextCompat.getDrawable(this@DialogueReportActivity, R.drawable.linechart_color_bg) }
                            ,this?.let { ContextCompat.getDrawable(this@DialogueReportActivity, R.drawable.linechart_color_bg1) }
                            ,this?.let { ContextCompat.getDrawable(this@DialogueReportActivity, R.drawable.linechart_color_bg2) }
                            ,this?.let { ContextCompat.getDrawable(this@DialogueReportActivity, R.drawable.linechart_color_bg3) }
                        )
                        callDurationColors = intArrayOf(
                            Color.parseColor("#A4D882")
                            ,Color.parseColor("#547DF9")
                            ,Color.parseColor("#FF8B12")
                            ,Color.parseColor("#FF3E38")
                        )
                    }
                }
                var lineChartHelper = LineChartHelper(line_chart)
                lineChartHelper.isLegend = true
                if (names.size==2){
                    lineChartHelper.legendSize = 12f
                    var mv = LineChartMarkView(this@DialogueReportActivity)
                    mv.chartView = line_chart
                    line_chart.marker = mv
                }else{
                    lineChartHelper.legendSize = 9f
                    var mv = LineChartMarkView1(this@DialogueReportActivity)
                    mv.chartView = line_chart
                    line_chart.marker = mv
                }
                lineChartHelper.showMultipleLineChart(it.data3, names, callDurationColors.toMutableList(), 6)
                lineChartHelper.toggleFilled(drawables,callDurationColors,true)
            })
        }
    }

    private val blockListener =
        View.OnClickListener { view ->
            val cell =
                view.tag as Cell
            if (cell != null) {
            }
        }

    override fun onSelectTimeChange(position: Int, data: String?) {
        when(data){
            "本周" ->{
                requestBannerViewModel.getExcelData(names,TimeUtlis.getWeekDays1(0))
            }
            "上周" ->{
                requestBannerViewModel.getExcelData(names,TimeUtlis.getWeekDays1(-1))
            }
            "本月" ->{
                requestBannerViewModel.getExcelData(names,TimeUtlis.getDays(TimeUtlis.getBeginDayOfMonth(),TimeUtlis.getEndDayOfMonth()))
            }
        }
    }
}