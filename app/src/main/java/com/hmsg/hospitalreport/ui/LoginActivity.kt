package com.hmsg.hospitalreport.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hmsg.hospitalreport.R
import com.hmsg.hospitalreport.bean.UserAccount
import com.hmsg.hospitalreport.utils.immersiveDark
import kotlinx.android.synthetic.main.login_act_layout.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_act_layout)
        immersiveDark(login_content_cstl)

        login_go_tv.setOnClickListener {
            goLogin()
        }
    }

    private fun goLogin() {
        if (login_account_et.text.toString().isEmpty()){
            Toast.makeText(this,login_account_et.hint.toString(),Toast.LENGTH_SHORT).show()
            return
        }
        if (login_password_et.text.toString().isEmpty()){
            Toast.makeText(this,login_password_et.hint.toString(),Toast.LENGTH_SHORT).show()
            return
        }

        UserAccount.username  = login_account_et.text.toString()
        UserAccount.password  = login_password_et.text.toString()
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show()
        onBackPressed()
    }
}