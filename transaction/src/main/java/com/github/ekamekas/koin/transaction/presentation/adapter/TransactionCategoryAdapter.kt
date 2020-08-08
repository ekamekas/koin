package com.github.ekamekas.koin.transaction.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.ItemTransactionCategoryBinding
import com.github.ekamekas.koin.transaction.presentation.view_object.TransactionCategoryVO

/**
 * Transaction category item list adapter
 *
 * @param data transaction category data list
 * @param isEditable flag to indicate item is editable
 * @param onClick callback on item click
 *
 * @see RecyclerView
 */
class TransactionCategoryAdapter(
    private var isEditable: Boolean = false,
    private val onClick: ((TransactionCategoryVO) -> Unit)? = null
): ListAdapter<TransactionCategoryVO, TransactionCategoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    /**
     * Set flag to indicate item is editable
     */
    fun setEditable(isEditable: Boolean) {
        this.isEditable = isEditable
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_transaction_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), isEditable)
    }

    inner class ViewHolder(private val binding: ItemTransactionCategoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(category: TransactionCategoryVO, isEditable: Boolean) {
            binding.category = category
            binding.isEditable = isEditable
            binding.vContent
                .setOnClickListener { onClick?.invoke(category) }
        }

    }

    companion object {

        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<TransactionCategoryVO>() {
            override fun areItemsTheSame(
                oldItem: TransactionCategoryVO,
                newItem: TransactionCategoryVO
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TransactionCategoryVO,
                newItem: TransactionCategoryVO
            ): Boolean {
                return oldItem == newItem
            }
        }

    }


}