package com.doing.toxim.baselib.utils

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.doing.toxim.baselib.common.BaseApplication


object UiUtils {

    fun getContext():Context {
        return BaseApplication.mContext
    }

    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(getContext(), colorRes)
    }
}