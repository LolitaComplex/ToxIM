package com.doing.toxim.app.ui.activity

import com.doing.toxim.app.R
import com.doing.toxim.baselib.ui.activity.BaseActivity

class SearchActivity : BaseActivity(){

    companion object {
        const val SEARCH_TYPE = "Search_Type"
        const val TYPE_PERSON = 1000
        const val TYPE_GROUP = 1001
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        
    }
}