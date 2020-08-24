package com.hmsg.hospitalreport.utils

import android.content.Context
import com.hmsg.hospitalreport.App
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
const val SPFileName = "hospitalreport_user"
inline fun <reified R, T> R.defSP(default: T) = defSP("", default)
/**
 * 指定key用这个
 */
inline fun <reified R, T> R.defSP(key: String, default: T) = SPUtil(key, default, R::class.java.name)

class SPUtil<T>(val key: String, val defValue: T, val fileName: String = SPFileName) :
    ReadWriteProperty<Any?, T> {
    val sp by lazy {
        App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val temKey = if (key.isEmpty()) property.name else key
        return when (defValue) {
            is String -> sp.getString(temKey, defValue)
            is Boolean -> sp.getBoolean(temKey, defValue)
            is Int -> sp.getInt(temKey, defValue)
            is Float -> sp.getFloat(temKey, defValue)
            is Long -> sp.getLong(temKey, defValue)
            else -> throw IllegalArgumentException("类型错误")
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val temKey = if (key.isEmpty()) property.name else key
        with(sp.edit()) {
            when (value) {
                is String -> putString(temKey, value)
                is Boolean -> putBoolean(temKey, value)
                is Int -> putInt(temKey, value)
                is Float -> putFloat(temKey, value)
                is Long -> putLong(temKey, value)
                else -> throw IllegalArgumentException("类型错误")
            }
            commit()
        }
    }
}

fun cleanSP(fileName: String = SPFileName) {
    App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE).edit().clear().commit()
}