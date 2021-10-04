package com.example.tablesection

import com.sushobh.section.RViewSection

interface RViewSectionListener {

    fun itemRemoved(position : Int,section : RViewSection)
    fun itemRangeRemoved(position : Int,count : Int,section : RViewSection)

    fun itemAdded(position : Int,section : RViewSection)
    fun itemRangeAdded(position : Int,count : Int,section : RViewSection)

    fun itemChanged(position : Int,section : RViewSection)
    fun itemRangeChanged(position : Int,count : Int,section : RViewSection)

    fun getAdapterStartPosition(section : RViewSection) : Int
}