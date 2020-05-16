package com.github.ekamekas.koin.presentation.dashboard

import android.os.Handler
import com.github.ekamekas.baha.common.ext.toastInfo
import com.github.ekamekas.baha.core.presentation.activity.BaseActivityDataBinding
import com.github.ekamekas.baha.core.presentation.view_object.StateObserver
import com.github.ekamekas.koin.R
import com.github.ekamekas.koin.databinding.ActivityDashboardBinding

/**
 * Center activity for user interaction
 */
class DashboardActivity: BaseActivityDataBinding<DashboardViewModel, ActivityDashboardBinding>() {

    private var didBackPress = false  // flag to indicate whether user did back press

    override val viewModelClazz: Class<DashboardViewModel>
        get() = DashboardViewModel::class.java
    override val resourceId: Int
        get() = R.layout.activity_dashboard

    override fun setupBinding() {
        dataBinding.viewModel = viewModel
    }

    override fun setupObservers() {
        viewModel.onAddTransactionEvent.observe(this, StateObserver {
            delegateAddTransaction()
        })
    }

    override fun setupViews() {/*nop*/}

    override fun didSetup() {/*nop*/}

    override fun onBackPressed() {
        if(didBackPress) {
            super.onBackPressed()
        } else {
            toastInfo(getString(R.string.hint_back_press_to_exit))
            didBackPress = true  // user did back press
            Handler().postDelayed({
                didBackPress = false  // reset flag state
            }, 1500L)  // delayed for 1.5 s
        }
    }

    // delegate
    private fun delegateAddTransaction() {
        toastInfo(getString(R.string.hint_wip))
    }
}