package com.limbo.floatwindow.draggable

import android.view.MotionEvent
import android.view.View

/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 6:12 PM
 */
class MovingDraggable : BaseDraggable() {

    private var downX = 0f
    private var downY = 0f

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = event.rawX
                val moveY = event.rawY
                updateLocation((moveX - downX).toInt(), (moveY - downY).toInt())
            }
        }
        return false
    }
}