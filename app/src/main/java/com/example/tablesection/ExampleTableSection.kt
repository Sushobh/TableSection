package com.example.tablesection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.customviews.*
import com.example.tablesection.data.*
import com.example.tablesection.sectioning.RViewSectionListener
import com.example.tablesection.sticky.StickyHeadersLinearLayoutManager
import com.example.tablesection.sectioning.TableSection
import java.lang.Exception

class ExampleTableSection(viewTypes : ArrayList<Int>, listener: RViewSectionListener, val dummyData: DummyData, val context: Context, layoutManager:
StickyHeadersLinearLayoutManager<SectioningStickyAdapter>, viewInfoTag: ViewInfoTag, listView : RecyclerView, rowHeight : Int, headerRowHeight : Int) :
        TableSection(viewTypes,layoutManager,viewInfoTag,listView,rowHeight,headerRowHeight,listener) {

    val stickyColumnHeaderName = "Description"




    override fun getLevel2ViewHolder(view: Level2View): RecyclerView.ViewHolder {
        return Level2ViewHolder(view)
    }

    override fun getLevel3ViewHolder(view: Level3View): RecyclerView.ViewHolder {
        return Level3ViewHolder(view)
    }

    override fun getLevel4ViewHolder(view: Level4View): RecyclerView.ViewHolder {
        return Level4ViewHolder(view)
    }

    override fun getColumnWidth(columnPosition: Int): Int {
        return 400
    }

    override fun getColumnCount(): Int {
        return 10
    }



    override fun getLevel2CellView(columnPosition: Int,rootView : ViewGroup): View {
        val cellView  = LayoutInflater.from(rootView.context).inflate(R.layout.simple_text_cell,null)
        cellView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        return cellView
    }

    override fun getLevel3CellView(columnPosition: Int, rootViewGroup: ViewGroup): View {
        val cellView  = LayoutInflater.from(rootViewGroup.context).inflate(R.layout.simple_text_cell,null)
        cellView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        return cellView
    }

    override fun getLevel4CellView(columnPosition: Int, rootViewGroup: ViewGroup): View {
        val cellView  = LayoutInflater.from(rootViewGroup.context).inflate(R.layout.simple_text_cell,null)
        cellView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        return cellView
    }

    override fun onSortClicked(headerPos: Int, isSticky: Boolean) {
          dummyData.otherRows.reverse()
    }

    override fun getDataLength(): Int {
        return dummyData.otherRows.size
    }

    override fun getHeaderData(): TableHeaderRowView.TableHeaderData {
        val cols = arrayListOf<CellHeader>()
        for(i in 0..getColumnCount()-1){
            cols.add(CellHeader("Column ${i}", CellHeader.CellHeaderStatus.DEFAULT))
        }
        return TableHeaderRowView.TableHeaderData(CellHeader(stickyColumnHeaderName, CellHeader.CellHeaderStatus.ASC),cols)
    }


    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        super.bindViewHolder(viewHolder, position)
        if(viewHolder is Level1ViewHolder){

            val data = dummyData.mainRow
            viewHolder.view.header1.text = data.header1
            viewHolder.view.header2.text = data.header2
            viewHolder.view.header3.text = data.header3
        }
        if(viewHolder is Level2ViewHolder){
             val data = dummyData.otherRows[position-1] as Level2Data
             viewHolder.stickColTextView.text = data.stickCol
             viewHolder.col1.text = data.col1
             viewHolder.col2.text = data.col2
             viewHolder.col3.text = data.col3
             viewHolder.col4.text = data.col4
             viewHolder.col5.text = data.col5
             viewHolder.col6.text = data.col6
             viewHolder.col7.text = data.col7
             viewHolder.col8.text = data.col8
             viewHolder.col9.text = data.col9
             viewHolder.col10.text = data.col10
        }
        if(viewHolder is Level3ViewHolder){
            val data = dummyData.otherRows[position-1] as Level3Data
            viewHolder.stickColTextView.text = data.stickCol
            viewHolder.col1.text = data.col1
            viewHolder.col2.text = data.col2
            viewHolder.col3.text = data.col3
            viewHolder.col4.text = data.col4
            viewHolder.col5.text = data.col5
            viewHolder.col6.text = data.col6
            viewHolder.col7.text = data.col7
            viewHolder.col8.text = data.col8
            viewHolder.col9.text = data.col9
            viewHolder.col10.text = data.col10
        }
        if(viewHolder is Level4ViewHolder){
            val data = dummyData.otherRows[position-1] as Level4Data
            viewHolder.stickColTextView.text = data.stickCol
            viewHolder.col1.text = data.col1
            viewHolder.col2.text = data.col2
            viewHolder.col3.text = data.col3
            viewHolder.col4.text = data.col4
            viewHolder.col5.text = data.col5
            viewHolder.col6.text = data.col6
            viewHolder.col7.text = data.col7
            viewHolder.col8.text = data.col8
            viewHolder.col9.text = data.col9
            viewHolder.col10.text = data.col10
        }
    }

    override fun getViewType(position: Int): Int {
         if(position == 0){
             return viewTypes.get(0)
         }
         else {
             val data = dummyData.otherRows.get(position-1)
             when(data){
                 is Level2Data -> {
                     return viewTypes.get(1)
                 }
                 is Level3Data -> {
                     return viewTypes.get(2)
                 }
                 is Level4Data -> {
                     return viewTypes.get(3)
                 }
             }
         }
        throw Exception("Invalid viewtype")
    }


    class Level2ViewHolder(val view : Level2View) : RecyclerView.ViewHolder(view),HasScrollableView<Level2View>{
        val stickColTextView = view.getStickyColumnHolder().findViewById<TextView>(R.id.sticky_col_text_view)
        val col1 = view.getColumnHolder(0).findViewById<TextView>(R.id.textview)
        val col2 = view.getColumnHolder(1).findViewById<TextView>(R.id.textview)
        val col3 = view.getColumnHolder(2).findViewById<TextView>(R.id.textview)
        val col4 = view.getColumnHolder(3).findViewById<TextView>(R.id.textview)
        val col5 = view.getColumnHolder(4).findViewById<TextView>(R.id.textview)
        val col6 = view.getColumnHolder(5).findViewById<TextView>(R.id.textview)
        val col7 = view.getColumnHolder(6).findViewById<TextView>(R.id.textview)
        val col8 = view.getColumnHolder(7).findViewById<TextView>(R.id.textview)
        val col9 = view.getColumnHolder(8).findViewById<TextView>(R.id.textview)
        val col10 = view.getColumnHolder(9).findViewById<TextView>(R.id.textview)
        override fun getScrollableView(): Level2View {
            return view
        }
    }

    class Level3ViewHolder(val view : Level3View) : RecyclerView.ViewHolder(view),HasScrollableView<Level3View>{
        val stickColTextView = view.getStickyColumnHolder().findViewById<TextView>(R.id.sticky_col_text_view)
        val col1 = view.getColumnHolder(0).findViewById<TextView>(R.id.textview)
        val col2 = view.getColumnHolder(1).findViewById<TextView>(R.id.textview)
        val col3 = view.getColumnHolder(2).findViewById<TextView>(R.id.textview)
        val col4 = view.getColumnHolder(3).findViewById<TextView>(R.id.textview)
        val col5 = view.getColumnHolder(4).findViewById<TextView>(R.id.textview)
        val col6 = view.getColumnHolder(5).findViewById<TextView>(R.id.textview)
        val col7 = view.getColumnHolder(6).findViewById<TextView>(R.id.textview)
        val col8 = view.getColumnHolder(7).findViewById<TextView>(R.id.textview)
        val col9 = view.getColumnHolder(8).findViewById<TextView>(R.id.textview)
        val col10 = view.getColumnHolder(9).findViewById<TextView>(R.id.textview)
        override fun getScrollableView(): Level3View {
            return view
        }
    }

    class Level4ViewHolder(val view : Level4View) : RecyclerView.ViewHolder(view),HasScrollableView<Level4View>{
        val stickColTextView = view.getStickyColumnHolder().findViewById<TextView>(R.id.sticky_col_text_view)
        val col1 = view.getColumnHolder(0).findViewById<TextView>(R.id.textview)
        val col2 = view.getColumnHolder(1).findViewById<TextView>(R.id.textview)
        val col3 = view.getColumnHolder(2).findViewById<TextView>(R.id.textview)
        val col4 = view.getColumnHolder(3).findViewById<TextView>(R.id.textview)
        val col5 = view.getColumnHolder(4).findViewById<TextView>(R.id.textview)
        val col6 = view.getColumnHolder(5).findViewById<TextView>(R.id.textview)
        val col7 = view.getColumnHolder(6).findViewById<TextView>(R.id.textview)
        val col8 = view.getColumnHolder(7).findViewById<TextView>(R.id.textview)
        val col9 = view.getColumnHolder(8).findViewById<TextView>(R.id.textview)
        val col10 = view.getColumnHolder(9).findViewById<TextView>(R.id.textview)
        override fun getScrollableView(): Level4View {
            return view
        }
    }

}