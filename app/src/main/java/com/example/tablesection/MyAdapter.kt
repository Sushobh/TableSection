package com.example.tablesection

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.RViewSectionListener
import com.example.tablesection.SectionGroupWithSticky
import com.example.tablesection.StickyHeaders
import com.sushobh.section.RViewSection
import java.lang.Exception

class MyAdapter(private  val sectionGroup : SectionGroupWithSticky<*>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    RViewSectionListener,
    StickyHeaders by sectionGroup,
    StickyHeaders.ViewSetup by sectionGroup{



    init {
        sectionGroup.listener = this
    }





    override fun getItemViewType(position: Int): Int {
        return sectionGroup.getViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         return sectionGroup.getViewHolder(viewType,parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
          sectionGroup.bindViewHolder(holder,position)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getItemCount(): Int {
       return sectionGroup.getLength()
    }

    override fun itemRemoved(position: Int, section: RViewSection) {
        notifyItemRemoved(position)
    }

    override fun itemRangeRemoved(position: Int, count: Int, section: RViewSection) {
          notifyItemRangeRemoved(position,count)
    }

    override fun itemAdded(position: Int, section: RViewSection) {
         notifyItemInserted(position)
    }

    override fun itemRangeAdded(position: Int, count: Int, section: RViewSection) {
          notifyItemRangeInserted(position,count)
    }

    override fun itemChanged(position: Int, section: RViewSection) {
          notifyItemChanged(position)
    }

    override fun itemRangeChanged(position: Int, count: Int, section: RViewSection) {
         notifyDataSetChanged()
    }

    override fun getAdapterStartPosition(section: RViewSection) : Int{
        return 0
    }






}