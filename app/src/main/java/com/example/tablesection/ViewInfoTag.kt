package com.sushobh.section

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