package com.example.tablesection

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

class Level1View : LinearLayout{


     lateinit var header1 : TextView
     lateinit var header2 : TextView
     lateinit var header3 : TextView

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
        inflate(context,R.layout.level1view,this)
        header1 = findViewById(R.id.header1)
        header2 = findViewById(R.id.header2)
        header3 = findViewById(R.id.header3)
    }



    fun addTableHeaderView(tableHeaderRowView: TableHeaderRowView){
        findViewById<LinearLayout>(R.id.main_holder).addView(tableHeaderRowView)
    }
}