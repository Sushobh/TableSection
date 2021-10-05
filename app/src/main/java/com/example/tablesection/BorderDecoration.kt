package com.example.tablesection

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.customviews.Level1View
import com.example.tablesection.customviews.Level2View
import com.example.tablesection.customviews.Level3View
import com.example.tablesection.customviews.Level4View

class BorderDecoration : RecyclerView.ItemDecoration() {


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        var top = -1
        var bottom = -1
        for(i in 0..parent.childCount){
          val child = parent.getChildAt(i)
          if(requiresYellowBorder(child)){
              val bounds = Rect()
              if(top == -1){
                  parent.getDecoratedBoundsWithMargins(child,bounds)
                  top = bounds.top

              }
              bottom = bottom+bounds.bottom
           }
        }
    }

    private fun requiresYellowBorder(child : View) : Boolean {
        return child is Level1View || child is Level2View || child is Level3View || child is Level4View
    }
}