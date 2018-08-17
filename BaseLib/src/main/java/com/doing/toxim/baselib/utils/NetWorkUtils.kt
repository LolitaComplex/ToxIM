package com.doing.toxim.baselib.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.doing.toxim.baselib.utils.UiUtils

/*
    网络工具
 */
object NetWorkUtils {

    /*
        判断网络是否可用
     */
    fun isNetWorkAvailable(): Boolean {
        val connectivityManager = UiUtils.getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    /*
        检测wifi是否连接
     */
    fun isWifiConnected(): Boolean {
        val cm = UiUtils.getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /*
        检测3G是否连接
     */
    fun is3gConnected(): Boolean {
        val cm = UiUtils.getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE
    }
}
