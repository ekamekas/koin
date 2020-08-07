package com.github.ekamekas.koin.presentation.splash

import android.content.Intent
import android.os.Handler
import androidx.core.app.TaskStackBuilder
import com.github.ekamekas.baha.core.presentation.activity.BaseActivity
import com.github.ekamekas.koin.R
import com.github.ekamekas.koin.presentation.dashboard.DashboardActivity
import com.github.ekamekas.koin.transaction.presentation.transaction_record.TransactionRecordActivity

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
        }, 500L)  // time will running for .5 s
    }

    // callback
    private fun onSplashTimeOut() {
        ifStartByLauncherElse(
            ifLaunched = {
                delegateDashboard()
            },
            ifElse = {
                onStartByShortcut()
            }
        )
    }
    private fun onStartByShortcut() {
        when(intent.action) {
            "ADD_TRANSACTION" -> {
                delegateAddTransactionCategory()
            }
            else -> {
                delegateDashboard()
            }
        }
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
    private fun delegateAddTransactionCategory() {
        TaskStackBuilder.create(this)
            .addNextIntent(
                Intent(this, DashboardActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
            )
            .addNextIntent(
                Intent(this, TransactionRecordActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
            )
            .startActivities()
            .also {
                finish()
            }
    }

    private fun ifStartByLauncherElse(ifLaunched: () -> Unit, ifElse: () -> Unit) {
        if(intent.action == Intent.ACTION_MAIN && intent.hasCategory(Intent.CATEGORY_LAUNCHER)) {
            ifLaunched.invoke()
        } else {
            ifElse.invoke()
        }
    }
}