package com.example.tablesection.sticky

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.tablesection.R
import com.example.tablesection.customviews.*

class ShadowDecoration : RecyclerView.ItemDecoration() {


    protected var drawable : Drawable? = null
    protected var shadowWidth = 20


    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)


        if (drawable == null) {
            drawable = parent.context.getDrawable(R.drawable.shadow_drawable)
        }


        val layoutManager = parent.layoutManager

        layoutManager?.let {
            if(it is StickyHeadersLinearLayoutManager<*>){
                val bounds = loadShadowBounds(it)
                drawBounds(bounds,parent,canvas,it)
            }
        }
    }

    private fun drawBounds(bounds: List<ShadowDecoration.ShadowBound>,parent: RecyclerView,canvas: Canvas,
                           layoutManager: StickyHeadersLinearLayoutManager<*>) {
        bounds.forEach {
            if(layoutManager.isStickyVisible() && it.top < layoutManager.getStickyHeight() ){
                drawable?.setBounds(parent.width-shadowWidth,it.top+layoutManager.getStickyHeight(),parent.width,it.bottom)
            }
            else {
                drawable?.setBounds(parent.width-shadowWidth,it.top,parent.width,it.bottom)
            }
            drawable?.draw(canvas)
        }
    }


    fun loadShadowBounds(layoutManager : RecyclerView.LayoutManager) : List<ShadowBound>{

        val bounds = arrayListOf<ShadowBound>()
        var i = 0
        val childCount = layoutManager.childCount
        while (i < childCount) {
            val childView = layoutManager.getChildAt(i)

            if (childView is Level2View || childView is Level3View || childView is Level4View) {
                val levelView = childView as RowView

                val viewInfoTag = levelView.tag as ViewInfoTag
                val currentTop = levelView.top
                var currentBottom = levelView.bottom
                if (i == childCount - 1) {
                    if (levelView.shouldShowShadow()) {
                        bounds.add(ShadowBound(currentTop, currentBottom))
                    }
                }
                var j = i + 1

                while (j < childCount) {
                    val nextView = layoutManager.getChildAt(j)
                    if (nextView == null || nextView.tag == null || !nextView.tag.equals(viewInfoTag)) {
                        if (levelView.shouldShowShadow()) {
                            bounds.add(ShadowBound(currentTop, currentBottom))
                        }
                        i = j
                        break
                    }
                    if(j == childCount -1){
                        if(levelView.shouldShowShadow()){
                            bounds.add(ShadowBound(currentTop, if(nextView.bottom > currentBottom) {
                                nextView.bottom
                            }else {
                                currentBottom
                            }))
                        }
                        i = j
                        break
                    }
                    else {
                        currentBottom = nextView.bottom
                        j++
                    }

                }
                if(i == childCount -1){

                }
                else {
                    continue
                }

            }
            i++
        }
        return bounds
    }


    class ShadowBound(val top : Int, val bottom : Int)


}