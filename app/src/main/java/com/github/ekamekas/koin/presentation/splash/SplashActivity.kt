package com.github.ekamekas.koin.presentation.splash

import android.os.Handler
import com.github.ekamekas.baha.common.ext.toastInfo
import com.github.ekamekas.baha.core.presentation.activity.BaseActivity
import com.github.ekamekas.koin.R

/**
 * The main entry activity, this activity should launch from the app launcher
 */
class SplashActivity: BaseActivity() {

    override val resourceId: Int
        get() = R.layout.activity_splash

    override fun setupViews() {/*nop*/}

    override fun didSetup() {
        startSplashTimer()
    }

    private fun startSplashTimer() {
        Handler().postDelayed({
            onSplashTimeOut()
        }, 1500L)  // time will running for 1.5 s
    }

    // callback
    private fun onSplashTimeOut() {
        toastInfo("On Progress")
    }
}