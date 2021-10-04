package com.example.tablesection

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi


class Level2View(context: Context, columnCount : Int) : RowView(context,columnCount) {

    private var stickyColumnView : View

    fun setStickyText(text : String){
        stickyColumnView.findViewById<TextView>(R.id.sticky_col_text_view).setText(text)
    }


    init {
        val stickyColumnView = View.inflate(context,R.layout.level2stickycolumn,null)
        stickyColumnView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        this.stickyColumnView = stickyColumnView
        addStickyColumn(stickyColumnView)
        setBackgroundColor(context.getColor(R.color.level2yellow))
    }




}