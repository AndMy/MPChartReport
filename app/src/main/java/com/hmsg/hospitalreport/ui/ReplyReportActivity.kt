package com.hmsg.hospitalreport.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.animation.Easing
import com.hmsg.hospitalreport.R
import com.hmsg.hospitalreport.adapter.ExcelPanelAdapter
import com.hmsg.hospitalreport.bean.Cell
import com.hmsg.hospitalreport.chart.barchart.BarChartHelper
import com.hmsg.hospitalreport.utils.TimeSelectView
import com.hmsg.hospitalreport.utils.TimeUtlis
import com.hmsg.hospitalreport.utils.immersiveDark
import com.hmsg.hospitalreport.viewmodel.DialogueReportViewModel
import kotlinx.android.synthetic.main.include_titlebar_layout.*
import kotlinx.android.synthetic.main.reply_report_act_layout.excelPanel
import kotlinx.android.synthetic.main.reply_report_act_layout.line_chart
import kotlinx.android.synthetic.main.reply_report_act_layout.time_select

/**
 * 咨询回复报表
 * */
class ReplyReportActivity : AppCompatActivity(R.layout.reply_report_act_layout) , TimeSelectView.TimeSelectInterFace {
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
        //time_select.setDefaultPosition(2)
        excelAdapter = ExcelPanelAdapter(this,blockListener,"日期")
        excelPanel.setAdapter(excelAdapter)
        names = mutableListOf("15分钟内回复患者数","15分钟以上回复患者数","无回复患者数")
        requestBannerViewModel.getExcelBarData(names,TimeUtlis.getWeekDays1(0))
        requestBannerViewModel.run {
            excelBarList.observe(this@ReplyReportActivity, Observer {
                excelAdapter.setAllData(it.data1,it.data,it.data2)
                //线图
                if (!line_chart.isEmpty)line_chart.clear()
                val chartColors = mutableListOf(
                    Color.parseColor("#0097D1")
                    ,Color.parseColor("#FF6184")
                    ,Color.parseColor("#FFA758"))
                BarChartHelper.Builder()
                    .setContext(this@ReplyReportActivity)
                    .setBarChart(line_chart)
                    .setBarSetData(it.data3)
                    .setLabels(names)
                    .setDisplayCount(3)
                    .setLegendEnable(true)
                    .setLegendTextSize(10f)
                    .setyAxisRightEnable(false)
                    .setDrawGridLines(false)
                    .setScaleEnabled(true)
                    .setDoubleTapToZoomEnabled(false)
                    .setDescriptionEnable(false)
                    .setPinchZoom(true)
                    .setGroupSpace(0.2f)
                    .setDurationMillis(2000)
                    .setEasing(Easing.EaseInOutExpo)
                    .setBarColors(chartColors)
                    .setXValueEnable(true)
                    .build()
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
                requestBannerViewModel.getExcelBarData(names,TimeUtlis.getWeekDays1(0))
            }
            "上周" ->{
                requestBannerViewModel.getExcelBarData(names,TimeUtlis.getWeekDays1(-1))
            }
            "本月" ->{
                requestBannerViewModel.getExcelBarData(names,TimeUtlis.getDays(TimeUtlis.getBeginDayOfMonth(),TimeUtlis.getEndDayOfMonth()))
            }
        }
    }
}