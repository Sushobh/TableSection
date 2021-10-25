package com.example.tablesection

import com.example.tablesection.sectioning.RViewSection
import com.example.tablesection.sectioning.RViewSectionGroup
import com.example.tablesection.sectioning.RViewSectionListener

open class SingleSectionGroup(rViewSectionListener: RViewSectionListener) : RViewSectionGroup<RViewSection>(rViewSectionListener) {


    fun displaySection(section : RViewSection){
        if(sections.size > 0){
            removeSection(0)
        }
        addSection(section)
    }


}