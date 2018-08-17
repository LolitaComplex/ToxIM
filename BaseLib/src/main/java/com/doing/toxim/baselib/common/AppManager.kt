package com.doing.toxim.baselib.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

class AppManager private constructor(){

    companion object {
        val sInstance: AppManager by lazy { AppManager() }
    }

    private val mActivityStack = LinkedList<Activity>()

    fun addActivity(activity: Activity) {
        mActivityStack.push(activity)
    }

    fun finishActivity(activity: Activity) {
//        activity.finish()
        mActivityStack.remove(activity)
    }

    fun currentActivity(): Activity {
        return mActivityStack.first()
    }

    fun clearAllActivity() {
        mActivityStack.forEach{
            it.finish()
        }
        mActivityStack.clear()
    }

    fun exitApp(context: Context) {
        clearAllActivity()
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE)
        if (manager is ActivityManager) {
            manager.killBackgroundProcesses(context.packageName)
            Runtime.getRuntime().exit(0)
        }
    }
}