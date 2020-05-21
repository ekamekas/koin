package com.github.ekamekas.koin.transaction.presentation.transaction_record

import androidx.lifecycle.ViewModel
import com.github.ekamekas.baha.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TransactionRecordModule {

    @ContributesAndroidInjector
    abstract fun activityTransactionRecord(): TransactionRecordActivity

    @ContributesAndroidInjector
    abstract fun fragmentTransactionRecordForm(): TransactionRecordFormFragment

    @ContributesAndroidInjector
    abstract fun fragmentTransactionRecordOptionalForm(): TransactionRecordOptionalFormFragment

    @Binds
    @IntoMap
    @ViewModelKey(TransactionRecordViewModel::class)
    abstract fun bindTransactionRecordViewModel(viewModel: TransactionRecordViewModel): ViewModel

}