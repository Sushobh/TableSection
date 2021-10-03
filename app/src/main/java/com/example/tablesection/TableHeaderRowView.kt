package com.example.tablesection

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.view.marginLeft
import androidx.core.view.updateLayoutParams

class TableHeaderRowView(context: Context,columnCount : Int,var columnWidths : Array<Int>) : RowView(context,columnCount) {

    private lateinit var stickyColumnCell : CellHeaderView
    private  var cells = arrayListOf<CellHeaderView>()



    init {
        layoutParams = (LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT))

    }




    data class TableHeaderData(var stickyCell : CellHeader,var cellHeaderList : ArrayList<CellHeader>){

    }

    fun setData(data : TableHeaderData){
        stickyColumnCell = CellHeaderView(context)
        stickyColumnCell.layoutParams = LinearLayout.
            LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT).also {
                it.leftMargin = 20
        }
        addStickyColumn(stickyColumnCell, context.resources.getDimension(R.dimen.sticky_column_width).toInt())
        stickyColumnCell.bindData(data.stickyCell)
        for(i in 0..data.cellHeaderList.size-1){
            cells.add(CellHeaderView(context))
            addColumn(cells[i],columnWidths[i],i)
            cells[i].bindData(data.cellHeaderList[i])
        }
    }


    fun bindData(data : TableHeaderData){
        stickyColumnCell.bindData(data.stickyCell)
        for(i in 0..data.cellHeaderList.size-1){
            cells[i].bindData(data.cellHeaderList[i])
        }
    }




}