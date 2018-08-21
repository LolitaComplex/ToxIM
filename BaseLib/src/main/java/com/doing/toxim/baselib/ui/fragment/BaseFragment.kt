package com.doing.toxim.baselib.ui.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment : RxFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injection()
        initView()
    }

    // ============== 模板方法 ==================
    @LayoutRes
    abstract fun getLayoutId(): Int
    protected abstract fun injection()
    protected abstract fun initView()
}