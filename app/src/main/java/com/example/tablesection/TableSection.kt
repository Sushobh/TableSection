package com.example.tablesection

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushobh.section.RViewSection
import java.lang.Exception

abstract class TableSection(val viewTypes : ArrayList<Int>) : RViewSection(){


    abstract fun getLevel2ViewHolder(view : Level2View) : RecyclerView.ViewHolder
    abstract fun getLevel3ViewHolder(view : Level3View) : RecyclerView.ViewHolder
    abstract fun getLevel4ViewHolder(view : Level4View) : RecyclerView.ViewHolder

    abstract fun getColumnWidth(columnPosition: Int) : Int
    abstract fun getColumnCount() : Int
    abstract fun getHeaderColumns() : TableHeaderRowView.TableHeaderData
    abstract fun getLevel2CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View
    abstract fun getLevel3CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View
    abstract fun getLevel4CellView(columnPosition: Int,rootViewGroup: ViewGroup) : View



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
            return Level1ViewHolder(level1View)
        }
        else if(viewType.equals(viewTypes.get(1))){
               val level2View = Level2View(parent.context,getColumnCount())
               level2View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
               for(i in 0..getColumnCount()-1){
                   level2View.addColumn(getLevel2CellView(i,parent),getColumnWidth(i),i)
               }
               return getLevel2ViewHolder(level2View)
        }
        else if(viewType.equals(viewTypes.get(2))){
            val level3View = Level3View(parent.context,getColumnCount())
            level3View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
            for(i in 0..getColumnCount()-1){
                level3View.addColumn(getLevel3CellView(i,parent),getColumnWidth(i),i)
            }
            return getLevel3ViewHolder(level3View)
        }

        else if(viewType.equals(viewTypes.get(3))){
            val level4View = Level4View(parent.context,getColumnCount())
            level4View.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
            for(i in 0..getColumnCount()-1){
                level4View.addColumn(getLevel4CellView(i,parent),getColumnWidth(i),i)
            }
            return getLevel4ViewHolder(level4View)
        }


        throw Exception("invalid view type")
    }



    private fun getColumnWidths(): IntArray {
        val widthArray = IntArray(getColumnCount())
        for(i in 0..getColumnCount()-1){
            widthArray[i] = getColumnWidth(i)
        }
        return widthArray
    }
}