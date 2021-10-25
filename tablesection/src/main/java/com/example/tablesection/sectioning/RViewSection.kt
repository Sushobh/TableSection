package com.example.tablesection.sectioning

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RViewSection(private var listener : RViewSectionListener) {

    abstract fun getLength() : Int
    abstract fun hasViewType(viewType : Int) : Boolean
    abstract fun getViewHolder(viewType: Int,parent : ViewGroup) : RecyclerView.ViewHolder
    abstract fun bindViewHolder(viewHolder : RecyclerView.ViewHolder,position : Int)
    abstract fun getViewType(position: Int) : Int

    private var enableEventsToAdapter : Boolean = false

    private var dummyListener = object : RViewSectionListener {
        override fun itemRemoved(position: Int, section: RViewSection) {

        }

        override fun itemRangeRemoved(position: Int, count: Int, section: RViewSection) {

        }

        override fun itemAdded(position: Int, section: RViewSection) {

        }

        override fun itemRangeAdded(position: Int, count: Int, section: RViewSection) {

        }

        override fun itemChanged(position: Int, section: RViewSection) {

        }

        override fun itemRangeChanged(position: Int, count: Int, section: RViewSection) {

        }

        override fun getAdapterStartPosition(section: RViewSection): Int {
            return 0
        }


    }

    open fun enableEventsToAdapter(flag : Boolean){
        this.enableEventsToAdapter = flag
    }


    final fun getListener() : RViewSectionListener {
        if(enableEventsToAdapter) {
            return listener
        }
        else {
            return dummyListener
        }
    }


}