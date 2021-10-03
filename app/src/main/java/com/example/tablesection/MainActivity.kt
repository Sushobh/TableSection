package com.example.tablesection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tableHeaderView = TableHeaderRowView(this,5, arrayOf(400,400,400,400,400))

        tableHeaderView.setData(TableHeaderRowView.TableHeaderData(CellHeader("Description",CellHeader.CellHeaderStatus.DEFAULT),
            arrayListOf(CellHeader("GDP",CellHeader.CellHeaderStatus.DEFAULT),
                CellHeader("Capital",CellHeader.CellHeaderStatus.DEFAULT),
                CellHeader("Language",CellHeader.CellHeaderStatus.DEFAULT),
                CellHeader("Size",CellHeader.CellHeaderStatus.DEFAULT),
                CellHeader("Population",CellHeader.CellHeaderStatus.DEFAULT))
            ))

        val level1View = findViewById<Level1View>(R.id.level1view)
        level1View.header1.text = "Header 1"
        level1View.header2.text = "Header 2"
        level1View.header3.text = "Header 3"
        level1View.addTableHeaderView(tableHeaderView)
    }
}