package com.doing.toxim.baselib.utils

object DensityUtils{

    fun dip2px(dpValue: Float): Int {
        val scale = UiUtils.getContext().resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(pxValue: Float): Int {
        val scale = UiUtils.getContext().resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        val fontScale = UiUtils.getContext().resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }


    fun sp2px(spValue: Float): Int {
        val fontScale = UiUtils.getContext().resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}