package com.example.tablesection.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.tablesection.R
import com.example.tablesection.sticky.StickyHeadersLinearLayoutManager


open class RowView(
    context: Context?, var columnCount: Int,val requiredHeight : Int
) : LinearLayout(context), StickyHeadersLinearLayoutManager.Scrollable {

    protected  var stickyColumnHolder : LinearLayout
    protected  var scrollView : NoFlingScrollView
    private    var columnsHolder : LinearLayout
    private    var cellHoldersList = arrayListOf<LinearLayout>()
    protected var bottomDivider : View
    protected var bottomDividerBiggerGrey : View
    var infoIconSpace : LinearLayout? = null



    fun addFlingListener(flingListener: NoFlingScrollView.FlingListener){
        scrollView.flingListener = flingListener
    }



    init {
        inflate(context, R.layout.row_view, this)
        stickyColumnHolder = findViewById(R.id.sticky_column_cell)
        scrollView = findViewById(R.id.scroll_row)
        columnsHolder = findViewById(R.id.columns_holder)
        bottomDivider = findViewById(R.id.bottom_divider_scroll_view)
        bottomDividerBiggerGrey = findViewById(R.id.bottom_divider_bigger_grey)
        addColumnSpace()
    }

    fun setNewHeight(height : Int){
        getChildAt(0).layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,requiredHeight)
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

    fun addInfoIconSpace(width : Int){
        val params = LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT)
        val layout = LinearLayout(context)
        layout.layoutParams = params
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        columnsHolder.addView(layout)
        infoIconSpace = layout
    }

    fun getColumnHolder(pos : Int) : ViewGroup{
        return cellHoldersList[pos]
    }

    fun getStickyColumnHolder() : ViewGroup {
        return stickyColumnHolder
    }

    override fun flingForReal(velocityX: Int) {
        scrollView.flingForReal(velocityX)
    }

    override fun scrollTo(dx: Int){
        scrollView.scrollTo(dx, 0)
    }

    @SuppressLint("NewApi")
    override fun addHorizontalScrollListener(listener: OnScrollChangeListener?) {
        scrollView.setOnScrollChangeListener(listener)
    }


    fun shouldShowShadow() : Boolean {
        return scrollView.canScrollHorizontally(1)
    }

}