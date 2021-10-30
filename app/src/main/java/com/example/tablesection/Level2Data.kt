package com.example.tablesection.data

open  class DummyRowData(val stickCol : String,val col1 : String,val col2 : String,val col3 : String,val col4 : String,val col5 : String,val col6 : String,val col7 : String,
                   val col8 : String,val col9 : String,val col10 : String){
}

class Level2Data( stickCol : String, col1 : String, col2 : String,col3 : String, col4 : String, col5 : String, col6 : String, col7 : String,
                       col8 : String, col9 : String,col10 : String) : DummyRowData(stickCol,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10)

class Level3Data( stickCol : String, col1 : String, col2 : String,col3 : String, col4 : String, col5 : String, col6 : String, col7 : String,
                  col8 : String, col9 : String,col10 : String) : DummyRowData(stickCol,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10)

class Level4Data( stickCol : String, col1 : String, col2 : String,col3 : String, col4 : String, col5 : String, col6 : String, col7 : String,
                  col8 : String, col9 : String,col10 : String) : DummyRowData(stickCol,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10)

class Level1Data(val header1 : String,val header2 : String,val header3 : String)


class DummyData(val mainRow : Level1Data,val otherRows : ArrayList<DummyRowData>)


fun getDummyData() : DummyData {

    val mainRow = Level1Data(getRandomString(8), getRandomString(8), getRandomString(8))
    val level2Count = 2
    val list = arrayListOf<DummyRowData>()
    val dummy = getLevel2Dummy(8)
    for(i in 1..level2Count){
        list.add(getLevel2Dummy(10))
        for(j in 1..level2Count){
            list.add(getLevel3Dummy(10))
            for(k in 1..level2Count){
                list.add(getLevel4Dummy(10))
            }
        }
    }
   return DummyData(mainRow,list)
}

fun getLevel2Dummy(stringLength : Int) : Level2Data {
    return Level2Data(getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        )
}

fun getLevel3Dummy(stringLength : Int) : Level3Data {
    return Level3Data(getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
    )
}

fun getLevel4Dummy(stringLength : Int) : Level4Data {
    return Level4Data(getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
        getRandomString(stringLength),
    )
}

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}