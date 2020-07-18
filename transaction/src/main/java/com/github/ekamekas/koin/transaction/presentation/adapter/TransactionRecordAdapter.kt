package com.github.ekamekas.koin.transaction.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.ekamekas.baha.common.databinding.ItemSimpleTextBinding
import com.github.ekamekas.baha.common.ext.dip
import com.github.ekamekas.baha.common.ext.toDaysOfTheWeekOrDate
import com.github.ekamekas.baha.common.ext.toEpochDay
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.ItemTransactionRecordBinding
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord

/**
 * Transaction record item list adapter
 *
 * @see RecyclerView
 */
class TransactionRecordAdapter(
    data: List<TransactionRecord> = listOf(),
    private val onClick: ((TransactionRecord) -> Unit)? = null
): RecyclerView.Adapter<TransactionRecordAdapter.ViewHolder>() {

    private var mData = listOf<ListItem>()

    init {
        mData = mapByDate(data)
    }

    /**
     * Adapter data setter
     */
    fun setData(data: List<TransactionRecord>) {
        this.mData = mapByDate(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ListType.DATE -> {
                val bindView: ItemSimpleTextBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_simple_text,
                    parent,
                    false
                )
                DateViewHolder(bindView)
            }
            ListType.DATA -> {
                val bindView: ItemTransactionRecordBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_transaction_record,
                    parent,
                    false
                )
                ItemViewHolder(bindView,onClick)
            }
            else -> {
                throw IllegalStateException("list type is not defined")
            }
        }
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            mData[position] is ListItem.DateItem -> {
                ListType.DATE
            }
            mData[position] is ListItem.DataItem -> {
                ListType.DATA
            }
            else -> {
                super.getItemViewType(position)
            }
        }
    }

    // map data to header-content flat data structure based on date
    private fun mapByDate(data: List<TransactionRecord>): List<ListItem> {
        val mData = mutableListOf<ListItem>()
        data
            .sortedByDescending { transaction -> transaction.transactionDate }
            .groupBy { transaction -> transaction.transactionDate.toEpochDay()  }
            .forEach { groupItem ->
                mData.addAll(
                    listOf<ListItem>(
                        ListItem.DateItem(groupItem.key)
                    ).plus(
                        groupItem.value
                            .map { item ->
                                ListItem.DataItem(item)
                            }
                    )
                )
            }
        return mData
    }

    abstract class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        abstract fun bind(item: ListItem)
    }

    class DateViewHolder(
        private val bindView: ItemSimpleTextBinding
    ): ViewHolder(bindView.root) {

        override fun bind(item: ListItem) {
            (item as? ListItem.DateItem)?.also { dateItem ->
                bindView.tvContent.apply {
                    setPadding(
                        0,this.context.dip(4),0,this.context.dip(4)
                    )
                }
                bindView.content = dateItem.value.toDaysOfTheWeekOrDate()
            }
        }

    }

    class ItemViewHolder(
        private val bindView: ItemTransactionRecordBinding,
        private val onClick: ((TransactionRecord) -> Unit)? = null
    ): ViewHolder(bindView.root) {

        override fun bind(item: ListItem) {
            (item as? ListItem.DataItem)?.also { dataItem ->
                bindView.transactionRecord = dataItem.value
                bindView.setOnClick { onClick?.invoke(dataItem.value) }
            }
        }

    }

    sealed class ListItem {
        data class DataItem(val value: TransactionRecord): ListItem()
        data class DateItem(val value: Long): ListItem()
    }

    private object ListType {
        const val DATE = 0
        const val DATA = 1
    }

}