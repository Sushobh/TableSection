package com.example.tablesection.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.example.tablesection.R

class CellHeaderView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    lateinit var textView: TextView

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    init {
        View.inflate(context, R.layout.hld_cell_header,this)
        gravity = Gravity.END
        layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        textView = findViewById(R.id.text_view)
    }

    fun setContentGravity(newGravity : Int){
        gravity = newGravity
    }


    fun bindData(cellHeader: CellHeader){
        when(cellHeader.cellStatus){
            CellHeader.CellHeaderStatus.ASC -> {
                textView.setText(cellHeader.text)
                findViewById<ImageView>(R.id.sort_indicator).setImageResource(R.drawable.hld_ic_sort_ascending)
            }
            CellHeader.CellHeaderStatus.DESC -> {
                textView.setText(cellHeader.text)
                findViewById<ImageView>(R.id.sort_indicator).setImageResource(R.drawable.hld_ic_sort_descending)
            }
            CellHeader.CellHeaderStatus.DEFAULT -> {
                textView.setText(cellHeader.text)
                findViewById<ImageView>(R.id.sort_indicator).setImageResource(R.drawable.hld_ic_sort)
            }
        }
        cellHeader.columnWidth?.let {
            val holderView = getChildAt(0)
            holderView.updateLayoutParams {
                width = it
            }
        }
    }
}