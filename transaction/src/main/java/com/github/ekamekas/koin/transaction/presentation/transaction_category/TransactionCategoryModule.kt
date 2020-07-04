package com.github.ekamekas.koin.transaction.presentation.transaction_category

import androidx.lifecycle.ViewModel
import com.github.ekamekas.baha.core.di.ViewModelKey
import com.github.ekamekas.koin.transaction.presentation.transaction_category.create.TransactionCategoryCreateActivity
import com.github.ekamekas.koin.transaction.presentation.transaction_category.create.TransactionCategoryCreateViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TransactionCategoryModule {

    // create
    @ContributesAndroidInjector
    abstract fun activityTransactionCategoryCreate(): TransactionCategoryCreateActivity

    @Binds
    @IntoMap
    @ViewModelKey(TransactionCategoryCreateViewModel::class)
    abstract fun bindTransactionCategoryCreateViewModel(viewModel: TransactionCategoryCreateViewModel): ViewModel

    // view
    @ContributesAndroidInjector
    abstract fun activityTransactionCategory(): TransactionCategoryActivity

    @Binds
    @IntoMap
    @ViewModelKey(TransactionCategoryViewModel::class)
    abstract fun bindTransactionCategoryViewModel(viewModel: TransactionCategoryViewModel): ViewModel

}