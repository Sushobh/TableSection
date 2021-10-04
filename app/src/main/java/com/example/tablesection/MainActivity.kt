package com.example.tablesection

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.data.MyAdapter
import com.example.tablesection.data.getDummyData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<RecyclerView>(R.id.list_view)
        listView.layoutManager = StickyHeadersLinearLayoutManager<MyAdapter>(this)

        val exampleTableSection = ExampleTableSection(arrayListOf(1,2,3,4), getDummyData(),this)
        val groupWithSticky = SectionGroupWithSticky<ExampleTableSection>()
        exampleTableSection.listener = groupWithSticky
        val adapter = MyAdapter(groupWithSticky)
        groupWithSticky.listener = adapter
        groupWithSticky.addSection(exampleTableSection)
        listView.adapter = adapter

    }
}