package com.doing.toxim.baselib.ui.helper

import android.support.annotation.IdRes
import com.doing.toxim.baselib.ui.activity.BaseActivity
import com.doing.toxim.baselib.ui.fragment.BaseFragment

class FragmentHelper(val mActivity: BaseActivity){

    fun addFragment(@IdRes id: Int, fragment: BaseFragment,  func: (fragment: BaseFragment) -> Unit) {
        addFragment(id, fragment, false, func)
    }

    fun replaceFragment(@IdRes id: Int, fragment: BaseFragment,  func: (fragment: BaseFragment) -> Unit) {
        replaceFragment(id, fragment, false, func)
    }

    fun addFragment(@IdRes id: Int, fragment: BaseFragment, isBackStack: Boolean,
                    func: (fragment: BaseFragment) -> Unit) {
        val transaction = mActivity.supportFragmentManager.beginTransaction()
        mActivity.mCurrentFragment?.let { transaction.remove(it) }
        transaction.add(id, fragment)
        transaction.show(fragment)
        transaction.commit()

        if (isBackStack) {
            transaction.addToBackStack(fragment.toString())
        }
        func.invoke(fragment)
    }

    fun replaceFragment(@IdRes id: Int, fragment: BaseFragment, isBackStack: Boolean,
                        func: (fragment: BaseFragment) -> Unit) {
        val transaction = mActivity.supportFragmentManager.beginTransaction()
        transaction.replace(id, fragment)

        if (isBackStack) {
            transaction.addToBackStack(fragment.toString())
        }
        transaction.commit()

        func.invoke(fragment)
    }
}