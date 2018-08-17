package com.doing.toxim.baselib.data.net

import com.doing.toxim.baselib.common.BaseConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitFactory{

    companion object {
        val sInstance: RetrofitFactory by lazy{ RetrofitFactory() }
    }

    protected val mRetrofit: Retrofit
    protected val mInterceptor: Interceptor
    protected val mOkHttpClient: OkHttpClient

    init {
        mInterceptor = Interceptor{chain ->
            chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("charset", "utf-8")
                    .build()
                    .let {
                        chain.proceed(it)
                    }
        }

        mOkHttpClient = initClient()

        mRetrofit = initRetrofit()
    }

    protected open fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BaseConstant.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build()
    }


    protected open fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(initLogInterceptor())
                .addInterceptor(mInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    private fun initLogInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun <T> create(clazz: Class<T>):T {
        return mRetrofit.create(clazz)
    }
}