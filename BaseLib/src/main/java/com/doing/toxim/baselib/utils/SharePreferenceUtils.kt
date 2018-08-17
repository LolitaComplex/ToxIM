package com.doing.toxim.baselib.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.doing.toxim.baselib.common.BaseConstant

/*
    SP工具类
 */
object AppPrefsUtils {
    private var sharePreference: SharedPreferences = UiUtils.getContext().getSharedPreferences(BaseConstant.TABLE_PREFS, Context.MODE_PRIVATE)
    private var editor: Editor

    init {
        editor = sharePreference.edit()
    }

    /*
        Boolean数据
     */
    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    /*
        默认 false
     */
    fun getBoolean(key: String): Boolean {
        return sharePreference.getBoolean(key, false)
    }

    /*
        String数据
     */
    fun putString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    /*
        默认 ""
     */
    fun getString(key: String): String {
        return sharePreference.getString(key, "")
    }

    /*
        Int数据
     */
    fun putInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    /*
        默认 0
     */
    fun getInt(key: String): Int {
        return sharePreference.getInt(key, 0)
    }

    /*
        Long数据
     */
    fun putLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    /*
        默认 0
     */
    fun getLong(key: String): Long {
        return sharePreference.getLong(key, 0)
    }

    /*
        Set数据
     */
    fun putStringSet(key: String, set: Set<String>) {
        val localSet = getStringSet(key).toMutableSet()
        localSet.addAll(set)
        editor.putStringSet(key, localSet)
        editor.apply()
    }

    /*
        默认空set
     */
    fun getStringSet(key: String): Set<String> {
        val set = setOf<String>()
        return sharePreference.getStringSet(key, set)
    }

    /*
        删除key数据
     */
    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }
}
