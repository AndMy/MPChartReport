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
import com.hmsg.hospitalreport.utils.immersiveDark
import com.hmsg.hospitalreport.viewmodel.DialogueReportViewModel
import kotlinx.android.synthetic.main.doctor_data_act_layout.excelPanel
import kotlinx.android.synthetic.main.doctor_data_act_layout.time_select
import kotlinx.android.synthetic.main.include_titlebar_layout.*

/**
 * 医生数据报表
 */
class DoctorDataActivity : AppCompatActivity(R.layout.doctor_data_act_layout), TimeSelectView.TimeSelectInterFace {
    private val requestBannerViewModel : DialogueReportViewModel by viewModels()
    lateinit var excelAdapter : ExcelPanelAdapter
    var names = mutableListOf("医生姓名","推荐用药次数","患者数","订单数","好评数","粉丝数","开单患者数","好评数","总分")
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
        excelAdapter = ExcelPanelAdapter(this,blockListener,"医生id")

        excelPanel.setAdapter(excelAdapter)

        requestBannerViewModel.doctorListData(names)
        requestBannerViewModel.run {
            excelList.observe(this@DoctorDataActivity, Observer {
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
                requestBannerViewModel.doctorListData(names)
            }
            "上周" ->{
                requestBannerViewModel.doctorListData(names)
            }
            "本月" ->{
                requestBannerViewModel.doctorListData(names)
            }
        }
    }
}