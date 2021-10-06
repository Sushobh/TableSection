package com.example.tablesection.customviews

class ViewInfoTag(val id : String) {


    override fun equals(other: Any?): Boolean {

        other?.let {
            if(it.equals(id)){
                return true
            }
        }

        return false
    }
}