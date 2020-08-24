package com.hmsg.hospitalreport.bean

import com.hmsg.hospitalreport.utils.defSP

object UserAccount {
    var username by defSP("username","")
    var password : String by defSP("password","")
}