package com.example.tablesection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.data.getDummyData
import com.example.tablesection.sectioning.SectionGroupWithSticky
import com.example.tablesection.sticky.StickyHeadersLinearLayoutManager
import com.example.tablesection.customviews.ViewInfoTag
import com.example.tablesection.data.DummyData
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<RecyclerView>(R.id.list_view)
        val layoutManager = StickyHeadersLinearLayoutManager<MyAdapter>(this)
        listView.layoutManager = layoutManager
        val headerRowHeight = 100
        val rowHeight = 150
        val exampleTableSection = ExampleTableSection(arrayListOf(1,2,3,4), getDummyData(),this,layoutManager,
            ViewInfoTag("1"),listView,rowHeight,headerRowHeight)
        val exampleTableSectio2 = ExampleTableSection(arrayListOf(5,6,7,8), getDummyData(),this,layoutManager,
                ViewInfoTag("2"),listView,rowHeight,headerRowHeight)
        val exampleTableSectio3 = ExampleTableSection(arrayListOf(9,10,11,12), getDummyData(),this,layoutManager,
                ViewInfoTag("3"),listView,rowHeight,headerRowHeight)
        val groupWithSticky = SectionGroupWithSticky<ExampleTableSection>()
        exampleTableSection.listener = groupWithSticky
        exampleTableSectio2.listener = groupWithSticky
        exampleTableSectio3.listener = groupWithSticky
        val adapter = MyAdapter(groupWithSticky)
        groupWithSticky.listener = adapter
        groupWithSticky.addSection(exampleTableSection)
        groupWithSticky.addSection(exampleTableSectio2)
        groupWithSticky.addSection(exampleTableSectio3)
        listView.adapter = adapter



    }


    suspend fun getData(){
        suspendCoroutine<DummyData> {
             getDummyData()
        }
    }


}