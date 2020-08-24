package com.hmsg.hospitalreport.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmsg.hospitalreport.R
import com.hmsg.hospitalreport.adapter.HomeAdapter
import com.hmsg.hospitalreport.bean.UserAccount
import com.hmsg.hospitalreport.utils.CommonItemDecoration
import com.hmsg.hospitalreport.utils.immersiveDark
import com.hmsg.hospitalreport.utils.px
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    var listData = arrayListOf("医患对话报表","咨询回复报表","医生注册时段报表","医患活跃度报表","门店销售报表","医生数据报表"
    ,"接诊超3小时报表")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        immersiveDark(main_top_tv)
        recycleView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(CommonItemDecoration(0,12.px(),12.px()))
            adapter = HomeAdapter(listData,this@MainActivity)
            var adapter = adapter as HomeAdapter
            adapter.setOnItemClick(object : HomeAdapter.OnItemClick{
                override fun setOnItemClick(view: View?, position: Int) {
                    val intent : Intent
                    when(listData.get(position)){
                        "医患对话报表" ->{
                            intent = Intent(this@MainActivity,DialogueReportActivity::class.java)
                            intent.putExtra("status","1")
                            intent.putExtra("title",listData.get(position))
                            startActivity(intent)
                        }
                        "咨询回复报表" ->{
                            intent = Intent(this@MainActivity,ReplyReportActivity::class.java)
                            intent.putExtra("title",listData.get(position))
                            startActivity(intent)
                        }
                        "医生注册时段报表" ->{
                            intent = Intent(this@MainActivity,DoctorRegisteredActivity::class.java)
                            intent.putExtra("title",listData.get(position))
                            startActivity(intent)
                        }
                        "医患活跃度报表" ->{
                            intent = Intent(this@MainActivity,DialogueReportActivity::class.java)
                            intent.putExtra("title",listData.get(position))
                            intent.putExtra("status","2")
                            startActivity(intent)
                        }
                        "门店销售报表" ->{
                            intent = Intent(this@MainActivity,StoreSalesActivity::class.java)
                            intent.putExtra("title",listData.get(position))
                            startActivity(intent)
                        }
                        "医生数据报表" ->{
                            intent = Intent(this@MainActivity,DoctorDataActivity::class.java)
                            intent.putExtra("title",listData.get(position))
                            startActivity(intent)
                        }
                        "接诊超3小时报表" ->{
                            intent = Intent(this@MainActivity,TimeoutActivity::class.java)
                            intent.putExtra("title",listData.get(position))
                            startActivity(intent)
                        }
                    }
                }
            })
        }
        if (UserAccount.username.isEmpty()){
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}