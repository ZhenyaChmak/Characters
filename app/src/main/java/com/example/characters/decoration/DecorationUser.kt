package com.example.characters.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addDecorationUser(bottomDecorator: Int) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val item = parent.adapter?.itemCount ?: return
            val position = parent.getChildAdapterPosition(view)
            if (position != item - 1)
                outRect.bottom = bottomDecorator
        }
    })
}
