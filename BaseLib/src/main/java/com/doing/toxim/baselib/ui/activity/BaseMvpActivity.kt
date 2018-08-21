package com.doing.toxim.baselib.ui.activity

import android.content.Context
import android.os.Bundle
import com.doing.toxim.baselib.common.BaseApplication
import com.doing.toxim.baselib.injection.component.ActivityComponent
import com.doing.toxim.baselib.injection.component.DaggerActivityComponent
import com.doing.toxim.baselib.injection.module.ActivityModule
import com.doing.toxim.baselib.injection.module.LifecycleProviderModule
import com.doing.toxim.baselib.presenter.BasePresenter
import com.doing.toxim.baselib.presenter.view.BaseView
import com.doing.toxim.baselib.ui.dialog.ProgressDialog
import javax.inject.Inject

abstract class BaseMvpActivity<T: BasePresenter<*>> : BaseActivity(), BaseView {

    // ============== 注入相关 ==================
    protected lateinit var mActivityComponent: ActivityComponent
    @Inject
    protected lateinit var mPresenter: T
    @Inject
    protected lateinit var mContext: Context

    private lateinit var mLoadProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).mAppComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

        mLoadProgressDialog = ProgressDialog.newInstance()

        super.onCreate(savedInstanceState)
    }


    // ============== 功能性方法 ==================
    override fun showLoading() {
        mLoadProgressDialog.show(supportFragmentManager)
    }

    override fun hiddenLoading() {
        mLoadProgressDialog.dismiss()
    }

    override fun onError() {}
}