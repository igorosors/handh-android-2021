package com.example.lesson_4_strelyukhin

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyDividerItemDecoration(private var verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = verticalSpaceHeight
        outRect.right = verticalSpaceHeight
        outRect.left = verticalSpaceHeight
        outRect.top = verticalSpaceHeight

    }
}