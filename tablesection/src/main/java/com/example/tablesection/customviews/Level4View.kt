package com.example.tablesection.customviews

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.example.tablesection.R
import com.example.tablesection.convertDpToPixel

class Level4View(context: Context, columnCount : Int,requiredHeight : Int) : RowView(context,columnCount,requiredHeight) {

    private var stickyColumnView : View

    fun setStickyText(text : String){
        stickyColumnView.findViewById<TextView>(R.id.sticky_col_text_view).setText(text)
    }


    init {
        val stickyColumnView = View.inflate(context, R.layout.level4stickycolumn,null)
        stickyColumnView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
        this.stickyColumnView = stickyColumnView
        addStickyColumn(stickyColumnView)
        bottomDivider.setBackgroundColor(context.getColor(R.color.divider_grey))
    }

    fun showBiggerGreyBottomDivider(){
        bottomDivider.visibility = GONE
        bottomDividerBiggerGrey.visibility = View.VISIBLE
    }




}