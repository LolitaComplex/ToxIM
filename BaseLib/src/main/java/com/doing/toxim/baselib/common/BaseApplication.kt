package com.doing.toxim.baselib.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.doing.toxim.baselib.injection.component.AppComponent
import com.doing.toxim.baselib.injection.component.DaggerAppComponent
import com.doing.toxim.baselib.injection.module.AppModule

class BaseApplication : Application() {

    lateinit var mAppComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        BaseApplication.mContext = this

        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context

    }
}