package com.toxim.provider.data.net

import com.google.gson.*
import com.google.gson.internal.bind.TreeTypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

class JsonManager private constructor(){

    companion object {
        val sInstance: JsonManager by lazy{ JsonManager() }
    }

    val mJson: Gson

    init {
        val builder = GsonBuilder()
        builder.registerTypeAdapterFactory(SpecialTypeAdapterFactory())
        mJson = builder.create()
    }

    internal class SpecialTypeAdapterFactory : TypeAdapterFactory {

        private val mJsonParser: Gson = GsonBuilder().create()
        private val mTypeList = ArrayList<Class<*>>()

        init {
            // TODO 添加异常解析处理的Entity
//            mTypeList.add()
        }

        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
            var deserializer: JsonDeserializer<T>? = null
            val rawType = type.rawType
            for (clazz in mTypeList) {
                if (rawType == clazz) {
                    deserializer = SpecialAdapter()
                    break
                }
            }
            return TreeTypeAdapter(null, deserializer, gson, type, this)
        }

        private inner class SpecialAdapter<T> : JsonDeserializer<T> {

            @Throws(JsonParseException::class)
            override fun deserialize(json: JsonElement, typeOfT: Type,
                                     context: JsonDeserializationContext): T? {
                return try {
                    mJsonParser.fromJson<T>(json, typeOfT)
                } catch (e: Exception) {
                    null
                }
            }
        }


    }
}
