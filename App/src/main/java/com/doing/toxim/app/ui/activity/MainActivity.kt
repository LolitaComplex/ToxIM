package com.doing.toxim.app.ui.activity

import com.doing.toxim.app.R
import com.doing.toxim.app.ui.fragment.ContactFragment
import com.doing.toxim.app.ui.fragment.GroupFragment
import com.doing.toxim.app.ui.fragment.HomeFragment
import com.doing.toxim.baselib.ui.activity.BaseActivity
import com.doing.toxim.baselib.ui.helper.NavHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class MainActivity : BaseActivity() {

    private val mNavHelper: NavHelper<String> by lazy {
        NavHelper<String>(this, R.id.mContainer, supportFragmentManager)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mToolbar = find(R.id.mToolbar)

        mNavHelper.add(R.id.action_home, NavHelper.Tab(HomeFragment::class.java, "Active"))
                .add(R.id.action_group, NavHelper.Tab(GroupFragment::class.java, "Group"))
                .add(R.id.action_contact, NavHelper.Tab(ContactFragment::class.java, "Contact"))

        mBottomNavigation.setOnNavigationItemSelectedListener{
            mNavHelper.performClickMenu(it.itemId)
        }
        mNavHelper.mListener = {newTab, _ ->
            mToolbar?.setGeneralTitle(newTab.extra)
        }
        mNavHelper.performClickMenu(R.id.action_home)
    }


}
