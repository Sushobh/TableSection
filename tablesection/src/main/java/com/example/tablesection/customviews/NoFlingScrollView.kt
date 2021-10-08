package com.example.tablesection.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView

class NoFlingScrollView : HorizontalScrollView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    public interface FlingListener {
        fun onFling(velocity : Int)
    }

    var flingListener : FlingListener? = null

    override fun fling(velocityX: Int) {
        //flingListener?.onFling(velocityX)
    }

    fun flingForReal(velocityX: Int){
        super.fling(velocityX)
    }
}