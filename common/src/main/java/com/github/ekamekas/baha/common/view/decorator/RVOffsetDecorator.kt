package com.github.ekamekas.baha.common.view.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Decorator of [RecyclerView] to give offset gap between items
 *
 * @param offset offset gap
 * @param orientation orientation of recycler view
 *
 * @see RecyclerView
 *
 */
class RVOffsetDecorator(
    private val offset: Int = 0,
    private val orientation: Int = RecyclerView.HORIZONTAL
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val isFirstItem = parent.getChildAdapterPosition(view) == 0

        when (orientation) {
            RecyclerView.HORIZONTAL -> {
                outRect.set(if(!isFirstItem) offset else 0, 0, 0, 0)
            }
            RecyclerView.VERTICAL -> {
                outRect.set(0, if(!isFirstItem) offset else 0, 0, 0)
            }
            else -> throw IllegalStateException("Orientation not defined")
        }
    }
}