package com.example.lesson_6_strelyukhin

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyDividerItemDecoration(private var verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.right = verticalSpaceHeight
            outRect.left = verticalSpaceHeight
            outRect.bottom = verticalSpaceHeight
        } else {
            outRect.right = verticalSpaceHeight
            outRect.left = verticalSpaceHeight
            outRect.bottom = verticalSpaceHeight
            outRect.top = verticalSpaceHeight
        }
    }
}
