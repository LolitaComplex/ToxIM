package com.doing.toxim.baselib.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.view.Menu
import com.doing.toxim.baselib.common.AppManager
import com.doing.toxim.baselib.ui.helper.FragmentHelper
import com.doing.toxim.baselib.ui.fragment.BaseFragment
import com.doing.toxim.baselib.ui.widget.GeneralToolbar
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract open class BaseActivity: RxAppCompatActivity(){

    // ============== 通用成员 ==================
    protected var mToolbar: GeneralToolbar? = null

    var mCurrentFragment: BaseFragment? = null
        private set

    private val mFragmentHelper by lazy {
        FragmentHelper(this@BaseActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.sInstance.addActivity(this)

        if (initArgs(intent)) {
            setContentView(getLayoutId())
            injection()
            initView()

            mToolbar?.let {
                setSupportActionBar(mToolbar)
                supportActionBar!!.setDisplayShowTitleEnabled(false)
                initActionBar(supportActionBar!!)
            }
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.sInstance.finishActivity(this)

    }

    override fun onBackPressed() {
//        //写成Lambda形式更复杂了有没有
//        out@{currentFragment : BaseFragment? ->
//            currentFragment?.let {
//                if (it.onBackPressed()) {
//                    return@out
//                }
//            }
//            super.onBackPressed()
//        }.invoke(mCurrentFragment)


        if (mCurrentFragment != null && mCurrentFragment!!.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    // ============== GeneralToolbar相关的方法 ==================
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mToolbar?.let {
            menuInflater.inflate(it.mMenuId, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    // ============== 模板方法 ==================
    /**
     * 获取布局Xml的Id
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化布局内容
     */
    protected abstract fun initView()

    /**
     * 使用Dagger做注入
     */
    protected open fun injection(){}

    /**
     * 初始化参数
     */
    protected fun initArgs(intent: Intent): Boolean {
        return true
    }

    /**
     * 初始化ActionBar
     */
    protected open fun initActionBar(actionBar: ActionBar) {}
}