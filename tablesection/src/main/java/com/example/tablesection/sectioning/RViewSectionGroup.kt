package com.example.tablesection.sectioning

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

abstract class  RViewSectionGroup<X : RViewSection> (sectionViewListener : RViewSectionListener) :
        RViewSection(sectionViewListener), RViewSectionListener {


    protected val sections : ArrayList<X> = arrayListOf()


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
        getListener().itemRemoved(startIndex+position,this)
    }

    override fun itemRangeRemoved(position: Int, count: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        getListener().itemRangeRemoved(startIndex+position,count,this)
    }

    override fun itemAdded(position: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        getListener().itemAdded(startIndex+position,this)
    }

    override fun itemRangeAdded(position: Int, count: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        getListener().itemRangeAdded(startIndex+position,count,this)
    }

    override fun itemChanged(position: Int,section : RViewSection) {
        val startIndex = getSectionStartIndex(section)
        getListener().itemChanged(startIndex+position,this)
    }

    override fun itemRangeChanged(position: Int, count: Int, section: RViewSection) {
        val startIndex = getSectionStartIndex(section)
        getListener().itemRangeChanged(startIndex+position,count,this)
    }

    open fun addSection(section : X,position: Int){
        val lastValidIndex = sections.size
        if(position <= lastValidIndex){
            sections.add(position,section)
            val startIndexForSection = getSectionStartIndex(section)
            getListener().itemRangeAdded(startIndexForSection,section.getLength(),this)
            section.enableEventsToAdapter(true)
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
        getListener().itemRangeRemoved(startIndexForSection,section.getLength(),this)
        section.enableEventsToAdapter(false)
    }

    fun removeSection(index : Int){
        val section = sections[index]
        removeSection(section)
    }

    override fun getAdapterStartPosition(section: RViewSection) : Int {
        return getListener().getAdapterStartPosition(this) + getSectionStartIndex(section)
    }

    override fun enableEventsToAdapter(flag: Boolean) {
        super.enableEventsToAdapter(flag)
        sections.forEach {
            it.enableEventsToAdapter(flag)
        }
    }
}