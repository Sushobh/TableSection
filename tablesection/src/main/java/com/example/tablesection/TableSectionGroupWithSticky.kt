package com.example.tablesection

import com.example.tablesection.sectioning.RViewSectionListener
import com.example.tablesection.sectioning.SectionGroupWithSticky
import com.example.tablesection.sticky.StickyHeaders

class TableSectionGroupWithSticky : SectionGroupWithSticky,StickyHeaders {
    constructor(listener: RViewSectionListener) : super(listener)

    override fun isStickyHeader(position: Int): Boolean {
        sections.forEach {
            if(getSectionStartIndex(it) == position){
                return true
            }
        }
        return false
    }
}