package com.example.tablesection

import com.example.tablesection.sectioning.RViewSectionListener
import com.example.tablesection.sectioning.SectionGroupWithSticky

class MySectionGroupWithSticky : SectionGroupWithSticky {
    constructor(listener: RViewSectionListener) : super(listener)

    override fun isStickyHeader(position: Int): Boolean {
        sections.forEach {
            if(getListener().getAdapterStartPosition(it) == position){
                return true
            }
        }
        return false
    }
}