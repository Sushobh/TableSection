package com.example.tablesection.sectioning

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

import java.lang.Exception
import java.util.*

abstract class  RViewSectionGroup<X : RViewSection> (val sections : ArrayList<X>) : RViewSection(), RViewSectionListener {




    @RequiresApi(Build.VERSION_CODES.N)
    override fun getLength(): Int {
        return sections.stream().map { it.getLength() }.reduce(0,{ total,element ->
            total+element
        })
    }

    override fun getViewType(position: Int): Int {
       val viewType =  getSectionForPosition(position)?.
                getViewType(position-getSectionStartIndex(position))
        if(viewType != null){
            return viewType
        }
        throw Exception("Invalid position")
    }

    override fun hasViewType(viewType: Int): Boolean {
        return sections.find { it.hasViewType(viewType) } != null
    }

    override fun getViewHolder(viewType: Int,parent : ViewGroup): RecyclerView.ViewHolder {
        val sectionWithViewType = sections.find { it.hasViewType(viewType) }
         sectionWithViewType?.let {
             return it.getViewHolder(viewType,parent)
         }
        throw Exception("Invalid viewtype")
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        getSectionForPosition(position)?.bindViewHolder(viewHolder,
            position-getSectionStartIndex(position))
    }

    fun getSectionForPosition(position : Int) : RViewSection? {
        var currentStartIndex = 0
        sections.forEachIndexed { index, rViewSection ->
            val currentRangeEnd = currentStartIndex + rViewSection.getLength()
            if(position <= currentRangeEnd-1){
                return rViewSection
            }
            currentStartIndex = currentRangeEnd
        }
        return null
    }

    fun getSectionStartIndex(position: Int) : Int{
        var currentStartIndex = 0
        sections.forEachIndexed { index, rViewSection ->
            val currentRangeEnd = currentStartIndex + rViewSection.getLength()
            if(position <= currentRangeEnd-1){
                 return currentStartIndex
            }
            currentStartIndex = currentRangeEnd
        }
        throw Exception("Invalid position")
    }

    fun getSectionStartIndex(section : RViewSection) : Int{
        var currentStartIndex = 0
        sections.forEachIndexed { index, rViewSection ->
            if(rViewSection == section){
                return currentStartIndex
            }
            val currentRangeEnd = currentStartIndex + rViewSection.getLength()
            currentStartIndex = currentRangeEnd
        }
        throw Exception("Invalid position")
    }

    override fun itemRemoved(position: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        listener.itemRemoved(startIndex+position,this)
    }

    override fun itemRangeRemoved(position: Int, count: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        listener.itemRangeRemoved(startIndex+position,count,this)
    }

    override fun itemAdded(position: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        listener.itemAdded(startIndex+position,this)
    }

    override fun itemRangeAdded(position: Int, count: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        listener.itemRangeAdded(startIndex+position,count,this)
    }

    override fun itemChanged(position: Int,section : RViewSection) {
        val startIndex = getSectionStartIndex(section)
        listener.itemChanged(startIndex+position,this)
    }

    override fun itemRangeChanged(position: Int, count: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        listener.itemRangeChanged(startIndex+position,count,this)
    }

    fun addSection(section : X,position: Int){
        val lastValidIndex = sections.size
        if(position <= lastValidIndex){
            sections.add(position,section)
            val startIndexForSection = getSectionStartIndex(section)
            listener.itemRangeAdded(startIndexForSection,section.getLength(),this)

        }
        else {
            throw Exception("Invalid position to add a new section to")
        }
    }

    fun addSection(section: X) {
        addSection(section,sections.size)
    }

    fun removeSection(section : RViewSection){
        if(!sections.contains(section)){
            throw Exception("Invalid position to add a new section to")
        }
        val startIndexForSection = getSectionStartIndex(section)
        sections.remove(section)
        listener.itemRangeRemoved(startIndexForSection,section.getLength(),this)
    }

    fun removeSection(index : Int){
        val section = sections[index]
        val startIndexForSection = getSectionStartIndex(section)
        sections.remove(section)
        listener.itemRangeRemoved(startIndexForSection,section.getLength(),this)
    }

    override fun getAdapterStartPosition(section: RViewSection) : Int {
        return listener.getAdapterStartPosition(this)+getSectionStartIndex(section)
    }
}