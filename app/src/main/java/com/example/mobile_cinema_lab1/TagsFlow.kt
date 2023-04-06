package com.example.mobile_cinema_lab1

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout

class TagsFlow (context: Context, attrs: AttributeSet?) : Flow(context, attrs) {
    fun setup(
        parentView: ViewGroup,
        tags: List<com.example.mobile_cinema_lab1.navigationmodels.Tag>
    ) {
        val referencedIds = IntArray(tags.size)
        for (i in tags.indices) {
            val tagView = createView(context)
            tagView.findViewById<TextView>(R.id.text).text = tags[i].tagName
            tagView.id = View.generateViewId()

            parentView.addView(tagView)
            referencedIds[i] = tagView.id
        }
        this.referencedIds = referencedIds
    }

    private fun createView(context: Context): ConstraintLayout {
        return inflate(context, R.layout.tag_item, null) as ConstraintLayout
    }
}