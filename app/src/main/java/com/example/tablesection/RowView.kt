package com.example.tablesection

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams


open class RowView(
    context: Context?, var columnCount: Int
) : LinearLayout(context) {

    protected  var stickyColumnHolder : LinearLayout
    protected  var scrollView : HorizontalScrollView
    private    var columnsHolder : LinearLayout
    private    var cellHoldersList = arrayListOf<LinearLayout>()






    init {
        inflate(context, R.layout.row_view, this)
        stickyColumnHolder = findViewById(R.id.sticky_column_cell)
        scrollView = findViewById(R.id.scroll_row)
        columnsHolder = findViewById(R.id.columns_holder)
        addColumnSpace()
    }

    private fun addColumnSpace(){
        for(i in 0..columnCount-1){
            val newCellHolder = LinearLayout(context)
            newCellHolder.layoutParams = LinearLayout.
            LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
            cellHoldersList.add(newCellHolder)
            columnsHolder.addView(newCellHolder)
        }
    }

    fun addStickyColumn(view: View){
        stickyColumnHolder.addView(view)
    }


    fun addColumn(view: View, parentWidth: Int, index: Int){
        cellHoldersList[index].updateLayoutParams {
            width = parentWidth
        }
        cellHoldersList[index].addView(view)
    }

    fun getColumnHolder(pos : Int) : ViewGroup{
        return cellHoldersList[pos]
    }

    fun getStickyColumnHolder() : ViewGroup {
        return stickyColumnHolder
    }

    fun scrollTo(dx: Int){
        scrollView.scrollTo(dx, 0)
    }

    companion object {
        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }
}