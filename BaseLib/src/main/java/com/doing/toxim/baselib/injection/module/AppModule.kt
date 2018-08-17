package com.doing.toxim.baselib.injection.module

import android.content.Context
import com.doing.toxim.baselib.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: BaseApplication) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }
}