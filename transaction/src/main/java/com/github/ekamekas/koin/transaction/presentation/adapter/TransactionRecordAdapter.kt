package com.github.ekamekas.koin.transaction.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.ItemTransactionRecordBinding
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord

/**
 * Transaction record item list adapter
 *
 * @see RecyclerView
 */
class TransactionRecordAdapter(
    private var data: List<TransactionRecord> = listOf(),
    private val onClick: ((TransactionRecord) -> Unit)? = null
): RecyclerView.Adapter<TransactionRecordAdapter.ViewHolder>() {

    /**
     * Adapter data setter
     */
    fun setData(data: List<TransactionRecord>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindView: ItemTransactionRecordBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_transaction_record,
            parent,
            false
        )
        return ViewHolder(bindView,onClick)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(
        private val bindView: ItemTransactionRecordBinding,
        private val onClick: ((TransactionRecord) -> Unit)? = null
    ): RecyclerView.ViewHolder(bindView.root) {

        fun bind(item: TransactionRecord) {
            bindView.transactionRecord = item
            bindView.setOnClick { onClick?.invoke(item) }
        }

    }

}