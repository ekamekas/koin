package com.github.ekamekas.koin.presentation.splash

import android.content.Intent
import android.os.Handler
import com.github.ekamekas.baha.core.presentation.activity.BaseActivity
import com.github.ekamekas.koin.R
import com.github.ekamekas.koin.presentation.dashboard.DashboardActivity

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
        delegateDashboard()
    }

    // delegate
    private fun delegateDashboard() {
        Intent(this, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }.also {
            startActivity(it)
        }.also {
            finish()
        }

    }
}