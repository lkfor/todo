package com.manabie.todo.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacingDecoration : ItemDecoration {
    private val spacing: Int
    private var displayMode: Int
    private var mIsExceptFirst = false
    private var mIsExceptLast = false

    @JvmOverloads
    constructor(spacing: Int, displayMode: Int = -1) {
        this.spacing = spacing
        this.displayMode = displayMode
    }

    constructor(
        spacing: Int,
        displayMode: Int,
        isExceptFirst: Boolean,
        isExceptLast: Boolean
    ) {
        this.spacing = spacing
        this.displayMode = displayMode
        mIsExceptFirst = isExceptFirst
        mIsExceptLast = isExceptLast
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(parent, outRect, layoutManager, position, itemCount)
    }

    private fun setSpacingForDirection(
        parent: RecyclerView,
        outRect: Rect,
        layoutManager: RecyclerView.LayoutManager?,
        position: Int,
        itemCount: Int
    ) { // Resolve display mode automatically
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager)
        }
        when (displayMode) {
            HORIZONTAL -> {
                outRect.left = if (position == 0) if (mIsExceptFirst) 0 else spacing else 0
                //                outRect.right = position == itemCount - 1 ? (mIsExceptLast ? 0 : spacing) : spacing;
                outRect.right = spacing
                outRect.top = 0
                outRect.bottom = 0
            }
            VERTICAL -> {
                outRect.left = 0
                outRect.right = 0
                outRect.top = if (position == 0) if (mIsExceptFirst) 0 else spacing else 0
                outRect.bottom =
                    if (position == itemCount - 1) if (mIsExceptLast) 0 else spacing else spacing
            }
            GRID -> if (layoutManager is GridLayoutManager) {
                val halfSpace = spacing / 2
                if (parent.paddingLeft != halfSpace) {
                    parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
                    parent.clipToPadding = false
                }

                outRect.top = halfSpace
                outRect.bottom = halfSpace
                outRect.left = halfSpace
                outRect.right = halfSpace
            }
        }
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is GridLayoutManager) return GRID
        return if (layoutManager!!.canScrollHorizontally()) HORIZONTAL else VERTICAL
    }

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val GRID = 2
    }
}