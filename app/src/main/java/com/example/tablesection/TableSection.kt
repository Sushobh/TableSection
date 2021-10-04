package com.example.tablesection

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.data.MyAdapter
import com.sushobh.section.RViewSection
import com.sushobh.section.ViewInfoTag
import java.lang.Exception

abstract class TableSection(val viewTypes : ArrayList<Int>,
                            val stickyHeadersLinearLayoutManager: StickyHeadersLinearLayoutManager<MyAdapter>,
                            val tag : ViewInfoTag,listView : RecyclerView
  ) : RViewSection(){


    abstract fun getLevel2ViewHolder(view : Level2View) : RecyclerView.ViewHolder
    abstract fun getLevel3ViewHolder(view : Level3View) : RecyclerView.ViewHolder
    abstract fun getLevel4ViewHolder(view : Level4View) : RecyclerView.ViewHolder

    abstract fun getColumnWidth(columnPosition: Int) : Int
    abstract fun getColumnCount() : Int
    abstract fun getHeaderColumns() : TableHeaderRowView.TableHeaderData
    abstract fun getLevel2CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View
    abstract fun getLevel3CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View
    abstract fun getLevel4CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View

    protected var currentScroll = 0

    private val scrollChangeListener : View.OnScrollChangeListener =  @SuppressLint("NewApi")

                    object : View.OnScrollChangeListener {
                        override fun onScrollChange(
                            v: View?,
                            scrollX: Int,
                            scrollY: Int,
                            oldScrollX: Int,
                            oldScrollY: Int
                        ) {
                            currentScroll = scrollX
                            syncScroll()
                        }
                    }

    init {
        listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                syncScroll()
            }
        })
    }


    class Level1ViewHolder(val view : Level1View) : RecyclerView.ViewHolder(view){

    }

    override fun hasViewType(viewType: Int): Boolean {
        return viewTypes.contains(viewType)
    }

    override fun getViewHolder(viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        if(viewType.equals(viewTypes.get(0))){
            val tableHeaderRowView = TableHeaderRowView(parent.context,getColumnCount(),getColumnWidths().toTypedArray())
            tableHeaderRowView.setData(getHeaderColumns())
            val level1View = Level1View(parent.context)
            level1View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
            level1View.addTableHeaderView(tableHeaderRowView)
            level1View.addHorizontalScrollListener(scrollChangeListener)
            level1View.tag = tag
            return Level1ViewHolder(level1View)
        }
        else if(viewType.equals(viewTypes.get(1))){
               val level2View = Level2View(parent.context,getColumnCount())
               level2View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
               for(i in 0..getColumnCount()-1){
                   level2View.addColumn(getLevel2CellView(i,parent),getColumnWidth(i),i)
               }
               level2View.addHorizontalScrollListener(scrollChangeListener)
               level2View.tag = tag
               return getLevel2ViewHolder(level2View)
        }
        else if(viewType.equals(viewTypes.get(2))){
            val level3View = Level3View(parent.context,getColumnCount())
            level3View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
            for(i in 0..getColumnCount()-1){
                level3View.addColumn(getLevel3CellView(i,parent),getColumnWidth(i),i)
            }
            level3View.addHorizontalScrollListener(scrollChangeListener)
            level3View.tag = tag
            return getLevel3ViewHolder(level3View)
        }

        else if(viewType.equals(viewTypes.get(3))){
            val level4View = Level4View(parent.context,getColumnCount())
            level4View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
            for(i in 0..getColumnCount()-1){
                level4View.addColumn(getLevel4CellView(i,parent),getColumnWidth(i),i)
            }
            level4View.addHorizontalScrollListener(scrollChangeListener)
            level4View.tag = tag
            return getLevel4ViewHolder(level4View)
        }


        throw Exception("invalid view type")
    }

    fun syncScroll(){
        for(i in 0..stickyHeadersLinearLayoutManager.childCount){
            val view = stickyHeadersLinearLayoutManager.getChildAt(i)
            view?.let {
                if(it.tag.equals(tag)){
                   if(it is StickyHeadersLinearLayoutManager.Scrollable){
                       it.scrollTo(currentScroll)
                   }
                }
            }
        }
    }



    private fun getColumnWidths(): IntArray {
        val widthArray = IntArray(getColumnCount())
        for(i in 0..getColumnCount()-1){
            widthArray[i] = getColumnWidth(i)
        }
        return widthArray
    }

    fun afterStickyIsLaidOut(stickyView: Level1View) {
        stickyView.scrollTo(currentScroll)
    }
}