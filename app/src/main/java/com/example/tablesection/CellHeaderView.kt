package com.example.tablesection

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams

class CellHeaderView : LinearLayout {
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
        View.inflate(context,R.layout.cell_header,this)
        gravity = Gravity.END
        layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
    }



    fun bindData(cellHeader: CellHeader){
        when(cellHeader.cellStatus){
            CellHeader.CellHeaderStatus.ASC -> {
                findViewById<TextView>(R.id.text_view).setText(context.getString(R.string.cell_header_up,cellHeader.text))
            }
            CellHeader.CellHeaderStatus.DESC -> {
                findViewById<TextView>(R.id.text_view).setText(context.getString(R.string.cell_header_down,cellHeader.text))
            }
            CellHeader.CellHeaderStatus.DEFAULT -> {
                findViewById<TextView>(R.id.text_view).setText(context.getString(R.string.cell_header_default,cellHeader.text))
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