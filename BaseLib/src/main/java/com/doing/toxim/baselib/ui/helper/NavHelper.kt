package com.doing.toxim.baselib.ui.helper

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.SparseArray


/**
 * 解决对Fragment的调度与重用问题，
 * 达到最优的Fragment切换
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
class NavHelper<T>(private val mContext: Context,
        private val mContainerId: Int,
        private val mFragmentManager: FragmentManager) {

    // 所有的Tab集合
    private val tabs = SparseArray<Tab<T>>()

    var mListener: ((newTab: Tab<T>, oldTab: Tab<T>?) -> Unit)? = null


    /**
     * 获取当前的显示的Tab
     *
     * @return 当前的Tab
     */
     var mCurrentTab: Tab<T>? = null
        private set

    /**
     * 添加Tab
     *
     * @param menuId Tab对应的菜单Id
     * @param tab    Tab
     */
    fun add(menuId: Int, tab: Tab<T>): NavHelper<T> {
        tabs.put(menuId, tab)
        return this
    }

    /**
     * 执行点击菜单的操作
     *
     * @param menuId 菜单的Id
     * @return 是否能够处理这个点击
     */
    fun performClickMenu(menuId: Int): Boolean {
        // 集合中寻找点击的菜单对应的Tab，
        // 如果有则进行处理
        val tab = tabs.get(menuId)
        if (tab != null) {
            doSelect(tab)
            return true
        }

        return false
    }

    /**
     * 进行真实的Tab选择操作
     *
     * @param tab Tab
     */
    private fun doSelect(tab: Tab<T>) {
        var oldTab: Tab<T>? = null

        if (mCurrentTab != null) {
            oldTab = mCurrentTab
            if (oldTab === tab) {
                // 如果说当前的Tab就是点击的Tab，
                // 那么我们不做处理
                notifyTabReselect(tab)
                return
            }
        }
        // 赋值并调用切换方法
        mCurrentTab = tab
        doTabChanged(mCurrentTab!!, oldTab)

    }

    /**
     * 进行Fragment的真实的调度操作
     * @param newTab 新的
     * @param oldTab 旧的
     */
    private fun doTabChanged(newTab: Tab<T>, oldTab: Tab<T>?) {
        val ft = mFragmentManager.beginTransaction()

        if (oldTab != null) {
            if (oldTab.fragment != null) {
                // 从界面移除，但是还在Fragment的缓存空间中
//                ft.detach(oldTab.fragment)
                ft.hide(oldTab.fragment)
            }
        }

        if (newTab.fragment == null) {
            // 首次新建
            val fragment = Fragment.instantiate(mContext, newTab.clx.name, null)
            // 缓存起来
            newTab.fragment = fragment
            // 提交到FragmentManger
            ft.add(mContainerId, fragment, newTab.clx.name)
        } else {
            // 从FragmentManger的缓存空间中重新加载到界面中
//            ft.attach(newTab.fragment)
        }
        ft.show(newTab.fragment)
        // 提交事务
        ft.commit()
        // 通知回调
        notifyTabSelect(newTab, oldTab)
    }

    /**
     * 回调我们的监听器
     *
     * @param newTab 新的Tab<T>
     * @param oldTab 旧的Tab<T>
    </T></T> */
    private fun notifyTabSelect(newTab: Tab<T>, oldTab: Tab<T>?) {
        mListener?.invoke(newTab, oldTab)
    }

    private fun notifyTabReselect(tab: Tab<T>) {
        // TODO 二次点击Tab所做的操作
    }

    /**
     * 我们的所有的Tab基础属性
     *
     * @param <T> 范型的额外参数
    </T> */
    class Tab<T>(// Fragment对应的Class信息
            var clx: Class<*>, // 额外的字段，用户自己设定需要使用
            var extra: T) {

        // 内部缓存的对应的Fragment，
        // Package权限，外部无法使用
        internal var fragment: Fragment? = null
    }

}
