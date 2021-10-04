package com.example.tablesection

import android.view.View
import com.sushobh.section.RViewSectionGroup

class SectionGroupWithSticky <X : TableSection>: RViewSectionGroup<X>(arrayListOf()),StickyHeaders,StickyHeaders.ViewSetup {


    override fun isStickyHeader(position: Int): Boolean {
        sections.forEach {
            if(getAdapterStartPosition(it) == position){
                return true
            }
        }
        return false
    }

    override fun setupStickyHeaderView(stickyHeader: View?) {

    }

    override fun teardownStickyHeaderView(stickyHeader: View?) {

    }

    override fun beforeLayingOut(view: View?) {

    }

    override fun afterStickyIsLaidOut(stickyView: View?) {

    }


}