package com.doing.toxim.baselib.injection.module

import android.app.Activity
import android.content.Context
import com.doing.toxim.baselib.common.BaseApplication
import com.doing.toxim.baselib.injection.ActivityScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    fun providesActivity(): Activity {
        return activity
    }
}