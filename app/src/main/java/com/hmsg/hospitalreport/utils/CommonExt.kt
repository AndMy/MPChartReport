package com.hmsg.hospitalreport.utils

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**扩展String函数判断字符串不为空且内容未null*/
fun String.isNotNull(): Boolean {
    return null != this && isNotEmpty() && !"null".equals(this, true)
}
/**dp转px*/
fun Int.px(): Int {
    return (Resources.getSystem().displayMetrics.density * this + 0.5f).roundToInt()
}

fun Long.transToString():String{//yyyy-MM-dd HH:mm
    return SimpleDateFormat("MM-dd", Locale.CHINA).format(this)
}
fun Long.transToString1():String{//yyyy-MM-dd HH:mm
    return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(this)
}
fun Int.todayBeforeAfterTime():Long{
    var calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, this)
    return calendar.timeInMillis
}