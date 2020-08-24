package com.hmsg.hospitalreport.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

@SuppressLint("ObsoleteSdkInt")
@JvmOverloads
fun Activity.immersive(
    view: View? = null,
    @ColorInt color: Int = DEFAULT_COLOR,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA
) {

    when {
        Build.VERSION.SDK_INT >= 21 -> {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = mixtureColor(color, alpha)
            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = systemUiVisibility
        }
        Build.VERSION.SDK_INT >= MIN_API -> {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setTranslucentView(window.decorView as ViewGroup, color, alpha)
        }
        Build.VERSION.SDK_INT > 16 -> {
            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = systemUiVisibility
        }
    }
    view?.statusPadding()
}
fun Activity.immersiveDark(
    view: View? = null,
    darkMode: Boolean = true,
    @ColorInt color: Int = DEFAULT_COLOR,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        darkModeForM(window, darkMode)
        immersive(color = color, alpha = alpha)
    } else if (isFlyme4) {
        darkModeForFlyme4(window, darkMode)
        immersive(color = color, alpha = alpha)
    } else if (isMIUI6) {
        darkModeForMIUI6(window, darkMode)
        immersive(color = color, alpha = alpha)
    } else {
        if (darkMode) {
            immersive(color = Color.LTGRAY)
        } else {
            immersive(color = color, alpha = alpha)
        }
    }

    view?.statusPadding()
}
fun Activity.setStatusBarColor(
    @ColorInt color: Int,
    darkMode: Boolean? = null,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float = 1F
) {
    when {
        Build.VERSION.SDK_INT >= 21 -> {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = mixtureColor(color, alpha)
        }
        Build.VERSION.SDK_INT >= MIN_API -> {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val content = window.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
            content.fitsSystemWindows = true
            setTranslucentView(window.decorView as ViewGroup, color, alpha)
        }
    }
    if (darkMode != null) {
        darkMode(darkMode)
    }
}

fun Activity.setStatusBarColorRes(
    @ColorRes colorRes: Int,
    darkMode: Boolean? = null,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float = 1F
) {
    val color = resources.getColor(colorRes)
    setStatusBarColor(color, darkMode, alpha = alpha)
}
fun Activity.darkMode(darkMode: Boolean = true) {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> darkModeForM(window, darkMode)
        isFlyme4 -> darkModeForFlyme4(window, darkMode)
        isMIUI6 -> darkModeForMIUI6(window, darkMode)
    }
}
@RequiresApi(Build.VERSION_CODES.M)
private fun darkModeForM(window: Window, darkMode: Boolean) {
    var systemUiVisibility = window.decorView.systemUiVisibility
    systemUiVisibility = if (darkMode) {
        systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
    window.decorView.systemUiVisibility = systemUiVisibility
}
private fun darkModeForFlyme4(window: Window?, dark: Boolean): Boolean {
    var result = false
    if (window != null) {
        try {
            val e = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField(
                "MEIZU_FLAG_DARK_STATUS_BAR_ICON"
            )
            val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(e)
            value = if (dark) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(e, value)
            window.attributes = e
            result = true
        } catch (var8: Exception) {
            Log.e("BarUtils", "darkIcon: failed")
        }

    }

    return result
}
@SuppressLint("PrivateApi")
private fun darkModeForMIUI6(window: Window, darkMode: Boolean): Boolean {
    val clazz = window.javaClass
    return try {
        val darkModeFlag: Int
        val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        darkModeFlag = field.getInt(layoutParams)
        val extraFlagField =
            clazz.getMethod(
                "setExtraFlags",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
        extraFlagField.invoke(window, if (darkMode) darkModeFlag else 0, darkModeFlag)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

}
fun View.statusPadding() {
    if (this is RelativeLayout) {
        throw UnsupportedOperationException("Unsupported set statusPadding for RelativeLayout")
    }
    if (Build.VERSION.SDK_INT >= MIN_API) {
        val lp = layoutParams
        if (lp != null && lp.height > 0) {
            lp.height += context.getStatusBarHeight() //增高
        }
        setPadding(
            paddingLeft, paddingTop + context.getStatusBarHeight(),
            paddingRight, paddingBottom
        )
    }
}
fun View.clearPaddingTop() {
    if (Build.VERSION.SDK_INT >= MIN_API) {
        setPadding(
            paddingLeft, 0,
            paddingRight, paddingBottom
        )
    }
}
fun View.statusMargin() {
    if (Build.VERSION.SDK_INT >= MIN_API) {
        val lp = layoutParams
        if (lp is ViewGroup.MarginLayoutParams) {
            lp.topMargin += context.getStatusBarHeight() //增高
        }
        layoutParams = lp
    }
}
private fun Context.setTranslucentView(
    container: ViewGroup, color: Int,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float
) {
    if (Build.VERSION.SDK_INT >= MIN_API) {
        val mixtureColor = mixtureColor(color, alpha)
        var translucentView: View? = container.findViewById(android.R.id.custom)
        if (translucentView == null && mixtureColor != 0) {
            translucentView = View(container.context)
            translucentView.id = android.R.id.custom
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()
            )
            container.addView(translucentView, lp)
        }
        translucentView?.setBackgroundColor(mixtureColor)
    }
}
// </editor-fold>

//<editor-fold desc="ActionBar">
fun AppCompatActivity.setActionBarBackground(@ColorInt color: Int) {
    supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
}

fun AppCompatActivity.setActionBarBackgroundRes(@ColorRes color: Int) {
    supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(color)))
}

fun AppCompatActivity.setActionBarTransparent() {
    supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}
//</editor-fold>

// <editor-fold desc="辅助">

/**
 * 计算颜色和透明度产生新的色值
 */
private fun mixtureColor(color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
    val a = if (color and -0x1000000 == 0) 0xff else color.ushr(24)
    return color and 0x00ffffff or ((a * alpha).toInt() shl 24)
}

/**
 * 是否有导航栏
 */
@SuppressLint("PrivateApi")
fun Context.isNavigationBar(): Boolean {
    var hasNavigationBar = false
    val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    if (id > 0) {
        hasNavigationBar = resources.getBoolean(id)
    }
    try {
        val systemPropertiesClass = Class.forName("android.os.SystemProperties")
        val m = systemPropertiesClass.getMethod("get", String::class.java)
        val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
        if ("1" == navBarOverride) {
            hasNavigationBar = false
        } else if ("0" == navBarOverride) {
            hasNavigationBar = true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return hasNavigationBar
}

fun Activity.setNavigationBarHidden() {

    if (Build.VERSION.SDK_INT in 12..18) {
        val v = window.decorView
        v.systemUiVisibility = View.GONE

    } else if (Build.VERSION.SDK_INT >= 19) {
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
        decorView.systemUiVisibility = uiOptions
    }
}


/**
 * 导航栏高度
 *
 * @return 如果当前设备存在导航栏返回导航栏高度, 否则返回0
 */
fun Context.getNavigationBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    val height = resources.getDimensionPixelSize(resourceId)

    return if (isNavigationBar()) {
        height
    } else {
        0
    }
}


/**
 * 状态栏高度
 */
fun Context.getStatusBarHeight(): Int {
    var result = 24
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    result = if (resId > 0) {
        resources.getDimensionPixelSize(resId)
    } else {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            result.toFloat(), Resources.getSystem().displayMetrics
        ).toInt()
    }
    return result
}

/**
 * 是否是Flyme4以上
 */
private val isFlyme4: Boolean
    get() = (Build.FINGERPRINT.contains("Flyme_OS_4")
            || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
            || Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE)
        .matcher(Build.DISPLAY)
        .find())

/**
 * 是否是MIUI6以上
 */
private val isMIUI6: Boolean
    @SuppressLint("PrivateApi")
    get() {
        return try {
            val clz = Class.forName("android.os.SystemProperties")
            val mtd = clz.getMethod("get", String::class.java)
            var `val` = mtd.invoke(null, "ro.miui.ui.version.name") as String
            `val` = `val`.replace("[vV]".toRegex(), "")
            val version = Integer.parseInt(`val`)
            version >= 6
        } catch (e: Exception) {
            false
        }
    }
private const val MIN_API = 19
private const val DEFAULT_COLOR = 0
private const val DEFAULT_ALPHA = 0f