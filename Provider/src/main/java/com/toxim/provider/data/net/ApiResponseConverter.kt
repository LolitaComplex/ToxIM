package com.toxim.provider.data.net

import android.util.Log
import com.doing.toxim.baselib.common.BaseConstant
import com.doing.toxim.baselib.data.rx.ApiException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class ApiResponseConverter<T>(private val json: Gson, val type: Type) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T? {
        val json = value.string()
        Log.i(BaseConstant.NET_JSON_TAG, json)
        val jObject = JSONObject(json)
        return try {
            val status = jObject.getInt("status")
            dealResult(status, jObject)
        } catch (e: JSONException) {
            "" as T
        } catch (e: JsonSyntaxException) {
            throw JsonSyntaxException("Json跟标签解析异常:\t + $json")
        }
    }

    private fun dealResult(status: Int, jsonObject: JSONObject): T?  {
        return if (status == 0) {
            when {
                type.toString().contains("String") -> jsonObject.getString("data") as T
                type.toString().contains("List") -> {
                    val data = jsonObject.getJSONArray("data")
                    json.fromJson<T>(data.toString(), this.type)
                }
                else -> {
                    val data = jsonObject.getJSONObject("data")
                    json.fromJson<T>(data.toString(), this.type)
                }
            }
        } else {
            val message = jsonObject.getString("message")
            throw ApiException(status, message)
        }
    }
}