package com.github.ekamekas.koin.transaction.presentation.transaction_record

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Adapter of transaction record page
 */
class TransactionRecordPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    val pages = listOf(
        TransactionRecordFormFragment(),
        TransactionRecordOptionalFormFragment()
    )

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = pages[position]
}