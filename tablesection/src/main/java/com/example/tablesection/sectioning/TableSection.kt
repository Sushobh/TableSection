package com.example.tablesection.sectioning

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.SectioningStickyAdapter
import com.example.tablesection.customviews.*
import com.example.tablesection.sticky.StickyHeadersLinearLayoutManager
import com.example.tablesection.customviews.ViewInfoTag
import java.lang.Exception
import kotlin.math.exp

abstract class TableSection(val viewTypes : ArrayList<Int>,
                            val stickyHeadersLinearLayoutManager: StickyHeadersLinearLayoutManager<SectioningStickyAdapter>,
                            val tag : ViewInfoTag,val listView : RecyclerView, val rowHeight : Int, val headerRowHeight : Int,
                            listener: RViewSectionListener) : RViewSection(listener){

    public interface HasScrollableView <X> where X : StickyHeadersLinearLayoutManager.Scrollable, X : RowView {
        fun getScrollableView() : X
    }

    protected var isExpanded : Boolean = true
    abstract fun getLevel2ViewHolder(view : Level2View) : RecyclerView.ViewHolder
    abstract fun getLevel3ViewHolder(view : Level3View) : RecyclerView.ViewHolder
    abstract fun getLevel4ViewHolder(view : Level4View) : RecyclerView.ViewHolder

    abstract fun getColumnWidth(columnPosition: Int) : Int
    abstract fun getColumnCount() : Int
    abstract fun getLevel2CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View
    abstract fun getLevel3CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View
    abstract fun getLevel4CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View
    abstract fun onSortClicked(headerPos: Int, isSticky: Boolean)
    abstract fun getDataLength() : Int
    abstract fun getHeaderData() : TableHeaderRowView.TableHeaderData

    protected var currentScroll = 0
    protected var infoIconSpaceWidth = 150
    private lateinit var headerCols : TableHeaderRowView.TableHeaderData

    private val scrollChangeListener : View.OnScrollChangeListener =  @SuppressLint("NewApi")

                    object : View.OnScrollChangeListener {
                        override fun onScrollChange(
                            v: View?,
                            scrollX: Int,
                            scrollY: Int,
                            oldScrollX: Int,
                            oldScrollY: Int
                        ) {
                            if(scrollX == 0){
                                v?.let {
                                    val childView = stickyHeadersLinearLayoutManager.findContainingItemView(v)
                                    val position = stickyHeadersLinearLayoutManager.getPosition(childView!!)
                                }

                            }
                            currentScroll = scrollX
                            syncScroll()
                        }
                    }

    private val flingListener : NoFlingScrollView.FlingListener = object : NoFlingScrollView.FlingListener {
        override fun onFling(velocity: Int) {
            for(i in 0..stickyHeadersLinearLayoutManager.childCount){
                val view = stickyHeadersLinearLayoutManager.getChildAt(i)
                view?.let {
                    if(it.tag.equals(tag)){
                        if(it is StickyHeadersLinearLayoutManager.Scrollable){
                            it.flingForReal(velocity)
                        }
                    }
                }
            }
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

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if(viewHolder is Level1ViewHolder){
            if(isExpanded){
                viewHolder.view.tableHeaderView.visibility = View.VISIBLE
                viewHolder.view.expandIndicator.scaleY = 1.0f
            }
            else {
                viewHolder.view.tableHeaderView.visibility = View.GONE
                viewHolder.view.expandIndicator.scaleY = -1.0f
            }
            viewHolder.view.tableHeaderView.bindData(headerCols)
        }
        if(viewHolder is HasScrollableView<*>){
            if(viewHolder.getScrollableView() is View){
                val view = viewHolder.getScrollableView()
                if(view is View){
                    view.post {
                        view.scrollTo(currentScroll)
                    }
                }
            }
        }

    }

    class Level1ViewHolder(val view : Level1View) : RecyclerView.ViewHolder(view){

    }

    override fun hasViewType(viewType: Int): Boolean {
        return viewTypes.contains(viewType)
    }

    fun getSortClickListener(): TableHeaderRowView.SortClickListener {
        return object : TableHeaderRowView.SortClickListener {
            override fun sortClicked(headerPos: Int, isSticky: Boolean) {
                if(isSticky){
                    if(headerCols.stickyCell.cellStatus == CellHeader.CellHeaderStatus.DEFAULT){
                        headerCols.stickyCell.cellStatus = CellHeader.CellHeaderStatus.DESC
                    }
                    else if(headerCols.stickyCell.cellStatus == CellHeader.CellHeaderStatus.ASC){
                        headerCols.stickyCell.cellStatus = CellHeader.CellHeaderStatus.DESC
                    }
                    else if(headerCols.stickyCell.cellStatus == CellHeader.CellHeaderStatus.DESC){
                        headerCols.stickyCell.cellStatus = CellHeader.CellHeaderStatus.ASC
                    }
                    headerCols.cellHeaderList.forEach {
                        it.cellStatus = CellHeader.CellHeaderStatus.DEFAULT
                    }
                }
                else {
                    headerCols.stickyCell.cellStatus = CellHeader.CellHeaderStatus.DEFAULT

                    if(headerCols.cellHeaderList[headerPos].cellStatus == CellHeader.CellHeaderStatus.DEFAULT){
                        headerCols.cellHeaderList[headerPos].cellStatus = CellHeader.CellHeaderStatus.DESC
                    }
                    else if(headerCols.cellHeaderList[headerPos].cellStatus == CellHeader.CellHeaderStatus.ASC){
                        headerCols.cellHeaderList[headerPos].cellStatus = CellHeader.CellHeaderStatus.DESC
                    }
                    else if(headerCols.cellHeaderList[headerPos].cellStatus == CellHeader.CellHeaderStatus.DESC){
                        headerCols.cellHeaderList[headerPos].cellStatus = CellHeader.CellHeaderStatus.ASC
                    }
                    headerCols.cellHeaderList.forEach {
                        if(it != headerCols.cellHeaderList[headerPos]){
                            it.cellStatus = CellHeader.CellHeaderStatus.DEFAULT
                        }
                    }
                }
                with(this@TableSection){
                    onSortClicked(headerPos,isSticky)
                    getListener().itemRangeChanged(0,getLength()-1,this)
                    android.os.Handler(Looper.getMainLooper()).postDelayed({
                        syncScroll()
                    },100)
                }
            }

        }
    }

    override fun getViewHolder(viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        if(viewType.equals(viewTypes.get(0))){
            val tableHeaderRowView = TableHeaderRowView(parent.context,getColumnCount(),getColumnWidths().toTypedArray(),getSortClickListener(),headerRowHeight)
            tableHeaderRowView.setData(getHeaderData().also { headerCols = it })
            tableHeaderRowView.addInfoIconSpace(infoIconSpaceWidth)
            tableHeaderRowView.setNewHeight(headerRowHeight)
            val level1View = Level1View(parent.context)
            level1View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
            level1View.addTableHeaderView(tableHeaderRowView)
            level1View.addHorizontalScrollListener(scrollChangeListener)
            level1View.tableHeaderView.addFlingListener(flingListener)
            level1View.translationZ = 100f
            level1View.tag = tag
            level1View.expandIndicator.setOnClickListener {
                 expand(!isExpanded)
            }
            return Level1ViewHolder(level1View)
        }
        else if(viewType.equals(viewTypes.get(1))){
               val level2View = Level2View(parent.context,getColumnCount(),rowHeight)
               level2View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
               for(i in 0..getColumnCount()-1){
                   level2View.addColumn(getLevel2CellView(i,parent),getColumnWidth(i),i)
               }
               level2View.setNewHeight(rowHeight)
               level2View.addInfoIconSpace(infoIconSpaceWidth)
               level2View.addHorizontalScrollListener(scrollChangeListener)
               level2View.addFlingListener(flingListener)
               level2View.tag = tag
               return getLevel2ViewHolder(level2View)
        }
        else if(viewType.equals(viewTypes.get(2))){
            val level3View = Level3View(parent.context,getColumnCount(),rowHeight)
            level3View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
            for(i in 0..getColumnCount()-1){
                level3View.addColumn(getLevel3CellView(i,parent),getColumnWidth(i),i)
            }
            level3View.setNewHeight(rowHeight)
            level3View.addInfoIconSpace(infoIconSpaceWidth)
            level3View.addHorizontalScrollListener(scrollChangeListener)
            level3View.addFlingListener(flingListener)
            level3View.tag = tag
            return getLevel3ViewHolder(level3View)
        }

        else if(viewType.equals(viewTypes.get(3))){
            val level4View = Level4View(parent.context,getColumnCount(),rowHeight)
            level4View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
            for(i in 0..getColumnCount()-1){
                level4View.addColumn(getLevel4CellView(i,parent),getColumnWidth(i),i)
            }
            level4View.setNewHeight(rowHeight)
            level4View.addInfoIconSpace(infoIconSpaceWidth)
            level4View.addHorizontalScrollListener(scrollChangeListener)
            level4View.addFlingListener(flingListener)
            level4View.tag = tag
            return getLevel4ViewHolder(level4View)
        }


        throw Exception("invalid view type")
    }

    fun expand(flag : Boolean){
        if(flag == isExpanded){
            return
        }
        if(isExpanded){
            isExpanded = false
            getListener().itemRangeRemoved(1,getDataLength(),this)
            getListener().itemChanged(0,this)
        }
        else {
            isExpanded = true
            getListener().itemRangeAdded(1,getDataLength(),this)
            getListener().itemChanged(0,this)
        }
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
        listView.invalidateItemDecorations()
    }

    override fun getLength(): Int {
        return if(!isExpanded){
            1
        }
        else {
            getDataLength()+1
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