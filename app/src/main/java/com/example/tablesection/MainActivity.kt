package com.example.tablesection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.data.getDummyData
import com.example.tablesection.sticky.StickyHeadersLinearLayoutManager
import com.example.tablesection.customviews.ViewInfoTag
import com.example.tablesection.data.DummyData
import com.example.tablesection.sticky.ShadowDecoration
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    var currentTab = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val listView = findViewById<RecyclerView>(R.id.list_view)
        listView.addItemDecoration(ShadowDecoration())
        val layoutManager = StickyHeadersLinearLayoutManager<SectioningStickyAdapter>(this)
        listView.layoutManager = layoutManager
        val headerRowHeight = 150
        val rowHeight = 220

        val adapter = SectioningStickyAdapter()


        val tab1 = TableSectionGroupWithSticky(adapter)
        val sec1tab1 = ExampleTableSection(arrayListOf(1,2,3,4), tab1,getDummyData(),this,layoutManager,
            ViewInfoTag("1"),listView,rowHeight,headerRowHeight)
        val sec2tab1 = ExampleTableSection(arrayListOf(5,6,7,8), tab1,getDummyData(),this,layoutManager,
                ViewInfoTag("2"),listView,rowHeight,headerRowHeight)
        tab1.addSection(sec1tab1)
        tab1.addSection(sec2tab1)


        val tab2 = TableSectionGroupWithSticky(adapter)
        val sec1tab2 = ExampleTableSection(arrayListOf(9,10,11,12), tab2,getDummyData(),this,layoutManager,
                ViewInfoTag("3"),listView,rowHeight,headerRowHeight)
        val sec2tab2 = ExampleTableSection(arrayListOf(13,14,15,116), tab2,getDummyData(),this,layoutManager,
                ViewInfoTag("4"),listView,rowHeight,headerRowHeight)

        tab2.addSection(sec1tab2)
        tab2.addSection(sec2tab2)


        val mainGroup = SingleSectionGroupWithSticky(adapter)
        mainGroup.enableEventsToAdapter(true)

        findViewById<Button>(R.id.togg).setOnClickListener {
            if(currentTab == 1){
                mainGroup.displaySection(tab2)
                currentTab = 2
            }
            else {
                mainGroup.displaySection(tab1)
                currentTab = 1
            }
        }
        adapter.sectionGroup = mainGroup
        listView.adapter = adapter
        mainGroup.displaySection(tab1)
    }






    suspend fun getData(){
        suspendCoroutine<DummyData> {
             getDummyData()
        }
    }


}