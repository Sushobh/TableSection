package com.example.tablesection

import com.example.tablesection.sectioning.RViewSectionListener
import com.example.tablesection.sticky.StickyHeaders

class SingleSectionGroupWithSticky(listener: RViewSectionListener) : SingleSectionGroup(listener),StickyHeaders {
    override fun isStickyHeader(position: Int): Boolean {

        sections.forEach {
            if(it is StickyHeaders){
                return it.isStickyHeader(position-getSectionStartIndex(it))
            }
        }

        return false
    }

}