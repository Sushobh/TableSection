package com.example.tablesection.customviews

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tablesection.R

class TableHeaderRowView(context: Context,columnCount : Int,var columnWidths : Array<Int>,val sortClickListener: SortClickListener,requiredHeight : Int) :
        RowView(context,columnCount,requiredHeight) {

    private lateinit var stickyColumnCell : CellHeaderView
    private  var cells = arrayListOf<CellHeaderView>()

    public interface SortClickListener {
        fun sortClicked(headerPos : Int,isSticky : Boolean)
    }



    init {
        layoutParams = (LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT))
        setBackgroundColor(Color.WHITE)
        bottomDivider.visibility = View.GONE
    }




    data class TableHeaderData(var stickyCell : CellHeader, var cellHeaderList : ArrayList<CellHeader>){

    }

    fun setData(data : TableHeaderData){
        stickyColumnCell = CellHeaderView(context)
        stickyColumnCell.layoutParams = LinearLayout.
            LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)

        stickyColumnCell.setOnClickListener {
            sortClickListener.sortClicked(-1,true)
        }
        stickyColumnCell.setContentGravity(Gravity.START)
        stickyColumnCell.paddingRight
        val stickyCellWithDivider = View.inflate(context, R.layout.table_header_sticky_cell,null)
        stickyCellWithDivider.findViewById<LinearLayout>(R.id.stick_cell_holder).addView(stickyColumnCell)
        stickyCellWithDivider.layoutParams = LinearLayout.
                    LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        addStickyColumn(stickyCellWithDivider)
        stickyColumnCell.bindData(data.stickyCell)
        for(i in 0..data.cellHeaderList.size-1){
            cells.add(CellHeaderView(context))
            addColumn(cells[i],columnWidths[i],i)
            cells[i].bindData(data.cellHeaderList[i])
            cells[i].setOnClickListener {
                sortClickListener.sortClicked(i,false)
            }
        }
    }


    fun bindData(data : TableHeaderData){
        stickyColumnCell.bindData(data.stickyCell)
        for(i in 0..data.cellHeaderList.size-1){
            cells[i].bindData(data.cellHeaderList[i])
        }
    }


    fun applyStyleOnText(  style : ( textView : TextView) -> Unit) {
        style.invoke(stickyColumnCell.textView)
        cells.forEach {
            style.invoke(it.textView)
        }
    }


}