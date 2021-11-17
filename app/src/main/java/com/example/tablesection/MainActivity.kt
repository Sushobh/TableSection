package com.example.tablesection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        listView.itemAnimator = null
        val layoutManager = StickyHeadersLinearLayoutManager<SectioningStickyAdapter>(this)
        listView.layoutManager = layoutManager
        val headerRowHeight = 100
        val rowHeight = 220

        val adapter = SectioningStickyAdapter()







        val mainGroup = SingleSectionGroupWithSticky(adapter)
        mainGroup.enableEventsToAdapter(true)

        findViewById<Button>(R.id.togg).setOnClickListener {

        }
        adapter.sectionGroup = mainGroup
        listView.adapter = adapter
        (0..1).forEach {
            Handler(Looper.getMainLooper()).postDelayed({
                val tab1 = TableSectionGroupWithSticky(adapter)
                val sec1tab1 = ExampleTableSection(arrayListOf(1,2,3,4), tab1,getDummyData(),this,layoutManager,
                        ViewInfoTag("1"),listView,rowHeight,headerRowHeight)
                tab1.addSection(sec1tab1)
                mainGroup.displaySection(tab1)
            }, (100*it).toLong())
        }
    }






    suspend fun getData(){
        suspendCoroutine<DummyData> {
             getDummyData()
        }
    }


}