package com.example.tablesection

class CellHeader(var text : String,var cellStatus : CellHeaderStatus,val columnWidth : Int? = null) {

    enum class CellHeaderStatus {
        ASC,DESC,DEFAULT
    }

}