package com.github.ekamekas.koin.transaction.presentation.transaction_category

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import com.github.ekamekas.baha.common.ext.*
import com.github.ekamekas.baha.core.presentation.activity.BaseActivityDataBinding
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.baha.core.presentation.view_object.StateObserver
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.ActivityTransactionCategoryBinding
import com.github.ekamekas.koin.transaction.presentation.ExtraCode
import com.github.ekamekas.koin.transaction.presentation.RequestCode
import com.github.ekamekas.koin.transaction.presentation.adapter.TransactionCategoryAdapter
import com.github.ekamekas.koin.transaction.presentation.transaction_category.create.TransactionCategoryCreateActivity
import com.github.ekamekas.koin.transaction.presentation.view_object.TransactionCategoryVO
import com.github.ekamekas.koin.transaction.presentation.view_object.toDomain
import com.github.ekamekas.koin.transaction.presentation.view_object.toVO

/**
 * Activity to view categories
 */
class TransactionCategoryActivity: BaseActivityDataBinding<TransactionCategoryViewModel, ActivityTransactionCategoryBinding>() {

    // adapter
    private val transactionCategoryAdapter by lazy {
        TransactionCategoryAdapter(
            isEditable = false,
            onClick = ::onTransactionCategoryClick
        )
    }

    // state
    private var isOnEditable: Boolean = false

    override val viewModelClazz: Class<TransactionCategoryViewModel>
        get() = TransactionCategoryViewModel::class.java
    override val resourceId: Int
        get() = R.layout.activity_transaction_category

    override fun setupBinding() {
        dataBinding.viewModel = viewModel
    }

    override fun setupObservers() {
        viewModel.onTransactionCategoryGetEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Success -> {
                    // set data to adapter
                    dataBinding.vContentLoading.hide()
                }
                is State.Error -> {
                    dataBinding.vContentLoading.hide()
                    toastError(
                        getString(com.github.ekamekas.baha.common.R.string.hint_get_operation_failed_format,
                            getString(R.string.label_transaction_category)
                        )
                    )
                }
                is State.Progress -> {
                    dataBinding.vContentLoading.show()
                }
            }.exhaustive
        })
        viewModel.onTransactionCategorySelectEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Success -> {
                    if(isOnEditable) {
                        delegateTransactionCategoryCreation(state.data.toVO())
                    } else {
                        onTransactionCategorySelected(state.data.toVO())
                    }
                }
                is State.Error -> {/*nop*/}
                is State.Progress -> {/*nop*/}
            }.exhaustive
        })
        viewModel.transactionCategory.observe(this, Observer { transactionCategory ->
            transactionCategoryAdapter.submitList(transactionCategory.map { it.toVO() }.sortedBy { it.name })
        })
    }

    override fun setupViews() {
        // setup navigation
        dataBinding.vNavigation.apply {
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_edit -> {
                        setEditableView(isEditable = true)

                        true
                    }
                    R.id.action_add -> {
                        delegateTransactionCategoryCreation()

                        true
                    }
                    else -> false
                }
            }
        }
        // setup transaction category list view
        dataBinding.vContent.apply {
            initVerticalRecyclerView(this@TransactionCategoryActivity, transactionCategoryAdapter)
        }
    }

    override fun didSetup() {/*nop*/}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RequestCode.TRANSACTION_CATEGORY -> {
                data?.getParcelableExtra<TransactionCategoryVO>(ExtraCode.TRANSACTION_CATEGORY)
                    ?.also { transactionCategory ->
                        if(transactionCategory.isDeleted) {
                            viewModel.deleteTransactionCategory(transactionCategory.toDomain())
                        } else {
                            viewModel.putTransactionCategory(transactionCategory.toDomain())
                        }
                    }
            }
        }
    }

    override fun onBackPressed() {
        if(isOnEditable) {
            setEditableView(isEditable = false)
        } else {
            super.onBackPressed()
        }
    }

    // set view based on editable condition
    private fun setEditableView(isEditable: Boolean) {
        isOnEditable = isEditable
        transactionCategoryAdapter.setEditable(isEditable)
        dataBinding.vNavigation.apply {
            navigationIcon = if(isEditable) {
                drawable(R.drawable.ic_navigation_color_on_primary)
            } else {
                drawable(R.drawable.ic_close_color_on_primary)
            }
            menu.apply {
                findItem(R.id.action_edit).isVisible = !isEditable
                findItem(R.id.action_add).isVisible = isEditable
            }
        }
    }

    // callback
    private fun onTransactionCategoryClick(transactionCategory: TransactionCategoryVO) {
        viewModel.getOrSelectTransactionCategory(
            transactionCategory = transactionCategory.toDomain()
        )
    }
    private fun onTransactionCategorySelected(transactionCategory: TransactionCategoryVO) {
        val resultIntent = Intent().apply {
            putExtra(ExtraCode.TRANSACTION_CATEGORY, transactionCategory)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    // delegator
    private fun delegateTransactionCategoryCreation(transactionCategory: TransactionCategoryVO? = null) {
        val requestIntent = Intent(this, TransactionCategoryCreateActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ExtraCode.TRANSACTION_CATEGORY, transactionCategory)
        }
        startActivityForResult(requestIntent, RequestCode.TRANSACTION_CATEGORY)
    }
}