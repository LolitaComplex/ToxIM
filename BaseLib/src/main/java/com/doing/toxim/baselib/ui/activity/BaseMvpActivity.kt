package com.doing.toxim.baselib.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.view.Menu
import com.doing.toxim.baselib.common.BaseApplication
import com.doing.toxim.baselib.injection.component.ActivityComponent
import com.doing.toxim.baselib.injection.component.DaggerActivityComponent
import com.doing.toxim.baselib.injection.module.ActivityModule
import com.doing.toxim.baselib.injection.module.LifecycleProviderModule
import com.doing.toxim.baselib.presenter.BasePresenter
import com.doing.toxim.baselib.presenter.view.BaseView
import com.doing.toxim.baselib.ui.dialog.ProgressDialog
import com.doing.toxim.baselib.ui.widget.GeneralToolbar
import javax.inject.Inject

abstract class BaseMvpActivity<T: BasePresenter<*>> : BaseActivity(), BaseView {

    // ============== 注入相关 ==================
    protected lateinit var mActivityComponent: ActivityComponent
    @Inject
    protected lateinit var mPresenter: T
    @Inject
    protected lateinit var mContext: Context

    // ============== 通用成员 ==================
    protected var mToolbar: GeneralToolbar? = null

    private lateinit var mLoadProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).mAppComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

        setContentView(getLayoutId())
        injection()
        initView()

        mToolbar?.let {
            setSupportActionBar(mToolbar)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            initActionBar(supportActionBar!!)
        }

        mLoadProgressDialog = ProgressDialog.newInstance()
    }

    // ============== GeneralToolbar相关的方法 ==================
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mToolbar?.let {
            menuInflater.inflate(it.mMenuId, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    // ============== 功能性方法 ==================
    override fun showLoading() {
        mLoadProgressDialog.show(supportFragmentManager)
    }

    override fun hiddenLoading() {
        mLoadProgressDialog.dismiss()
    }

    override fun onError() {}

    // ============== 模板方法 ==================
    @LayoutRes
    protected abstract fun getLayoutId(): Int
    protected abstract fun injection()
    protected abstract fun initView()

    protected open fun initActionBar(actionBar: ActionBar) {}
}