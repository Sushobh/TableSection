package com.example.tablesection.customviews

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tablesection.R


class Level2View(context: Context, columnCount : Int,requirdHeight : Int) : RowView(context,columnCount,requirdHeight) {

    private var stickyColumnView : View

    fun setStickyText(text : String){
        stickyColumnView.findViewById<TextView>(R.id.sticky_col_text_view).setText(text)
    }


    init {
        val stickyColumnView = View.inflate(context, R.layout.level2stickycolumn,null)
        stickyColumnView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        this.stickyColumnView = stickyColumnView
        addStickyColumn(stickyColumnView)
        setBackgroundColor(context.getColor(R.color.level2_background_light))
        bottomDivider.visibility = View.GONE
    }




}