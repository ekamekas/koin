package com.github.ekamekas.koin.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.github.ekamekas.baha.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DashboardModule {

    @ContributesAndroidInjector
    abstract fun activityDashboard(): DashboardActivity

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

}