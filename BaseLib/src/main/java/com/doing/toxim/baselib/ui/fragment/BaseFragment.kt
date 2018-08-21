package com.doing.toxim.baselib.ui.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment : RxFragment() {

    private var mContainer: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mContainer == null) {
            val view = inflater.inflate(getLayoutId(), container, false)
            initView(view)
            mContainer = view
        } else {
            if (mContainer!!.parent != null) {
                (mContainer!!.parent as ViewGroup).removeView(mContainer!!)
            }
        }
        return mContainer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injection()
    }

    fun onBackPressed(): Boolean {
        return false
    }


    // ============== 模板方法 ==================
    @LayoutRes
    abstract fun getLayoutId(): Int
    protected abstract fun injection()
    protected abstract fun initView(container: View)
}