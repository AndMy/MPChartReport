package com.hmsg.hospitalreport.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.animation.Easing
import com.hmsg.hospitalreport.R
import com.hmsg.hospitalreport.adapter.ExcelPanel2RowAdapter
import com.hmsg.hospitalreport.bean.Cell
import com.hmsg.hospitalreport.chart.barchart.BarChartHelper
import com.hmsg.hospitalreport.utils.TimeSelectView
import com.hmsg.hospitalreport.utils.immersiveDark
import com.hmsg.hospitalreport.viewmodel.DialogueReportViewModel
import kotlinx.android.synthetic.main.include_titlebar_layout.*
import kotlinx.android.synthetic.main.doctor_registered_act_layout.excelPanel
import kotlinx.android.synthetic.main.doctor_registered_act_layout.line_chart
import kotlinx.android.synthetic.main.doctor_registered_act_layout.time_select

/**
 * 医生注册时段报表
 */
class DoctorRegisteredActivity : AppCompatActivity(R.layout.doctor_registered_act_layout),
    TimeSelectView.TimeSelectInterFace  {
    private val requestBannerViewModel : DialogueReportViewModel by viewModels()
    lateinit var excelAdapter : ExcelPanel2RowAdapter
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
        excelAdapter = ExcelPanel2RowAdapter(this,blockListener)
        excelPanel.setAdapter(excelAdapter)
        names = mutableListOf("注册医生数")
        requestBannerViewModel.getSingleExcelBarData(names)
        requestBannerViewModel.run {
            excelSingleBarList.observe(this@DoctorRegisteredActivity, Observer {
                excelAdapter.setAllData(it.data1,it.data,it.data2)
                //线图
                BarChartHelper.Builder()
                    .setContext(this@DoctorRegisteredActivity)
                    .setBarChart(line_chart)
                    .setBarData(it.data3)
                    //.setDisplayCount(6)
                    .setLegendEnable(false)
                    .setLegendTextSize(10f)
                    .setValueTextSize(13f)
                    .setyAxisRightEnable(false)
                    .setDrawGridLines(false)
                    .setScaleEnabled(true)
                    .setDoubleTapToZoomEnabled(false)
                    .setDescriptionEnable(false)
                    .setPinchZoom(true)
                    .setBarWidth(0.6f)
                    .setDurationMillis(2000)
                    .setEasing(Easing.EaseInOutExpo)
                    .setBarColor(Color.parseColor("#3ABEF1"))
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
                requestBannerViewModel.getSingleExcelBarData(names)
            }
            "上周" ->{
                requestBannerViewModel.getSingleExcelBarData(names)
            }
            "本月" ->{
                requestBannerViewModel.getSingleExcelBarData(names)
            }
        }
    }
}