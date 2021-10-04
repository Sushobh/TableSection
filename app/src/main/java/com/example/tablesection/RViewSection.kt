package com.sushobh.section

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.RViewSectionListener

abstract class RViewSection() {
    lateinit var listener : RViewSectionListener
    abstract fun getLength() : Int
    abstract fun hasViewType(viewType : Int) : Boolean
    abstract fun getViewHolder(viewType: Int,parent : ViewGroup) : RecyclerView.ViewHolder
    abstract fun bindViewHolder(viewHolder : RecyclerView.ViewHolder,position : Int)
    abstract fun getViewType(position: Int) : Int
}