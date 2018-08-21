package com.doing.toxim.baselib.ui.fragment

import android.app.Activity
import android.content.Context
import com.doing.toxim.baselib.common.BaseApplication
import com.doing.toxim.baselib.injection.component.ActivityComponent
import com.doing.toxim.baselib.injection.component.DaggerActivityComponent
import com.doing.toxim.baselib.injection.module.ActivityModule
import com.doing.toxim.baselib.injection.module.LifecycleProviderModule
import com.doing.toxim.baselib.presenter.BasePresenter
import com.doing.toxim.baselib.presenter.view.BaseView
import com.doing.toxim.baselib.ui.dialog.ProgressDialog
import javax.inject.Inject

abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    // ============== 注入相关 ==================
    protected lateinit var mActivityComponent: ActivityComponent
    @Inject
    protected lateinit var mPresenter: T
    @Inject
    protected lateinit var mContext: Context

    private lateinit var mLoadProgressDialog: ProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComponent = (context.applicationContext as BaseApplication).mAppComponent

        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(ActivityModule(context as Activity))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

        mLoadProgressDialog = ProgressDialog.newInstance()
    }


    override fun showLoading() {
        mLoadProgressDialog.show(fragmentManager)
    }

    override fun hiddenLoading() {
        mLoadProgressDialog.dismiss()
    }

    override fun onError() {}
}