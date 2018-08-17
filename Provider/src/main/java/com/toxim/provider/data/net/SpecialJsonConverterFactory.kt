package com.toxim.provider.data.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class SpecialJsonConverterFactory private constructor(private val mJson: Gson): Converter.Factory() {

    companion object {
        fun create(json: Gson): SpecialJsonConverterFactory {
            return SpecialJsonConverterFactory(json)
        }

        fun create() : SpecialJsonConverterFactory {
            return create(GsonBuilder().create())
        }
    }

    private val mJsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(mJson)

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *> {
        return ApiResponseConverter<Any>(mJson, type)
    }

    override fun requestBodyConverter(type: Type,
                                      parameterAnnotations: Array<Annotation>,
                                      methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        return mJsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }
}