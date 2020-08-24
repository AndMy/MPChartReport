package com.hmsg.hospitalreport.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hmsg.hospitalreport.R
import com.hmsg.hospitalreport.adapter.ExcelPanelAdapter
import com.hmsg.hospitalreport.bean.Cell
import com.hmsg.hospitalreport.utils.TimeSelectView
import com.hmsg.hospitalreport.utils.TimeUtlis
import com.hmsg.hospitalreport.utils.immersiveDark
import com.hmsg.hospitalreport.viewmodel.DialogueReportViewModel
import kotlinx.android.synthetic.main.include_titlebar_layout.*
import kotlinx.android.synthetic.main.timeout_act_layout.excelPanel
import kotlinx.android.synthetic.main.timeout_act_layout.time_select

class TimeoutActivity :AppCompatActivity(R.layout.timeout_act_layout), TimeSelectView.TimeSelectInterFace {
    private val requestBannerViewModel : DialogueReportViewModel by viewModels()
    lateinit var excelAdapter : ExcelPanelAdapter
    var names = mutableListOf("接诊医生ID","接诊医生姓名","患者ID","患者姓名","最后问诊时间")
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
        excelAdapter = ExcelPanelAdapter(this,blockListener,"超时数")

        excelPanel.setAdapter(excelAdapter)

        requestBannerViewModel.timeOutListData(names,TimeUtlis.getWeekDays1(0))
        requestBannerViewModel.run {
            excelList.observe(this@TimeoutActivity, Observer {
                excelAdapter.setAllData(it.data1,it.data,it.data2)
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
                requestBannerViewModel.timeOutListData(names,TimeUtlis.getWeekDays1(0))
            }
            "上周" ->{
                requestBannerViewModel.timeOutListData(names,TimeUtlis.getWeekDays1(-1))
            }
            "本月" ->{
                requestBannerViewModel.timeOutListData(names,TimeUtlis.getDays(TimeUtlis.getBeginDayOfMonth(),TimeUtlis.getEndDayOfMonth()))
            }
        }
    }
}