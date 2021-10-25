package com.example.tablesection.sectioning

import android.view.View
import com.example.tablesection.sticky.StickyHeaders

abstract class SectionGroupWithSticky(listener: RViewSectionListener)  : RViewSectionGroup<RViewSection>(arrayListOf(),listener), StickyHeaders, StickyHeaders.ViewSetup {


    override fun setupStickyHeaderView(stickyHeader: View?) {

    }

    override fun teardownStickyHeaderView(stickyHeader: View?) {

    }

    override fun beforeLayingOut(view: View?) {

    }

    override fun afterStickyIsLaidOut(stickyView: View?) {

    }


}