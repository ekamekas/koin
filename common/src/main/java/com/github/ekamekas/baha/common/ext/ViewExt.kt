package com.github.ekamekas.baha.common.ext

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ekamekas.baha.common.view.decorator.RVOffsetDecorator

/**
 * Extension function to initialize recycler view as vertical
 *
 * @param context context
 * @param adapter adapter to attach to this recycler view
 * @param nestedScrollingEnable flag to indicate is recycler view is nested scrolling
 * @param offset offset gap for each items
 */
fun RecyclerView.initVerticalRecyclerView(
    context: Context,
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
    nestedScrollingEnable: Boolean = false,
    offset: Int = 0
) {
    this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    this.adapter = adapter
    this.isNestedScrollingEnabled = nestedScrollingEnable
    if(itemDecorationCount == 0) {  // do not have decorators on created
        addItemDecoration(
            RVOffsetDecorator(offset, RecyclerView.VERTICAL)
        )
    }
}

/**
 * Extension function to initialize recycler view as horizontal
 *
 * @param context context
 * @param adapter adapter to attach to this recycler view
 * @param nestedScrollingEnable flag to indicate is recycler view is nested scrolling
 * @param offset offset gap for each items
 */
fun RecyclerView.initHorizontalRecyclerView(
    context: Context,
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
    nestedScrollingEnable: Boolean = false,
    offset: Int = 0
) {
    this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    this.adapter = adapter
    this.isNestedScrollingEnabled = nestedScrollingEnable
    if(itemDecorationCount == 0) {  // do not have decorators on created
        addItemDecoration(
            RVOffsetDecorator(offset, RecyclerView.HORIZONTAL)
        )
    }
}

/**
 * Extension set visibility of the view based on function passed
 *
 * @param isInvisible flag to do invisible instead of gone if block function result false
 * @param block function that return flag to indicate this view is visible
 */
fun View.visibleIf(isInvisible: Boolean = false, block: () -> Boolean) {
    visibility = when {
        block.invoke() -> View.VISIBLE
        isInvisible -> View.INVISIBLE
        else -> View.GONE
    }
}