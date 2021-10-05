package com.example.tablesection.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tablesection.R
import com.example.tablesection.sticky.StickyHeadersLinearLayoutManager

class Level1View : LinearLayout, StickyHeadersLinearLayoutManager.Scrollable {


     lateinit var header1 : TextView
     lateinit var header2 : TextView
     lateinit var header3 : TextView
     lateinit var expandIndicator : ImageView
     lateinit var tableHeaderView : TableHeaderRowView

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.level1view,this)
        header1 = findViewById(R.id.header1)
        header2 = findViewById(R.id.header2)
        header3 = findViewById(R.id.header3)
        expandIndicator = findViewById(R.id.expand_indicator)

    }

    override fun flingForReal(velocityX: Int) {
        tableHeaderView.flingForReal(velocityX)
    }

    override fun scrollTo(dx: Int){
        tableHeaderView.scrollTo(dx)
    }

    override fun addHorizontalScrollListener(listener: OnScrollChangeListener?) {
        tableHeaderView.addHorizontalScrollListener(listener)
    }


    fun addTableHeaderView(tableHeaderRowView: TableHeaderRowView){
        findViewById<LinearLayout>(R.id.table_header_holder).addView(tableHeaderRowView)
        this.tableHeaderView = tableHeaderRowView
    }
}