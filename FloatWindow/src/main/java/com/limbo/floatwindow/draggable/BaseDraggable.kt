package com.limbo.floatwindow.draggable

import android.content.res.Resources
import android.view.View
import com.limbo.floatwindow.FloatWindow
import com.limbo.floatwindow.IFloatWindow
import kotlin.math.abs


/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 6:13 PM
 */
abstract class BaseDraggable : View.OnTouchListener {

    private val floatBuilder by lazy { FloatWindow.init() }
    private var floatWindow: IFloatWindow? = null

    fun bindingFloatWindow(floatWindow: IFloatWindow) {
        this.floatWindow = floatWindow
        floatBuilder.contentView?.setOnTouchListener(this)
    }

    /**
     * 更新位置信息
     */
    protected fun updateLocation(x: Int?, y: Int?) {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val viewWidth = floatBuilder.contentView?.width ?: 0
        val viewHeight = floatBuilder.contentView?.height ?: 0

        // 获取当前的 X 和 Y 坐标
        var newX = x ?: floatBuilder.absoluteXY.first
        var newY = y ?: floatBuilder.absoluteXY.second

        // 边界检测：确保窗口不超出屏幕的左、右、上、下边界
        newX = when {
            newX < 0 -> 0 // 防止超出左边界
            newX + viewWidth > screenWidth -> screenWidth - viewWidth // 防止超出右边界
            else -> newX
        }

        newY = when {
            newY < 0 -> 0 // 防止超出上边界
            newY + viewHeight > screenHeight -> screenHeight - viewHeight // 防止超出下边界
            else -> newY
        }

        // 贴边逻辑：如果窗口接近屏幕边缘，自动贴边
        val edgeThreshold = 30 // 你可以根据需要调整这个值
        if (newX < edgeThreshold) {
            newX = 0 // 靠近左边缘时吸附到左边
        } else if (newX + viewWidth > screenWidth - edgeThreshold) {
            newX = screenWidth - viewWidth // 靠近右边缘时吸附到右边
        }

        // 更新位置
        floatBuilder.setAbsoluteXY(newX, newY)
        floatWindow?.notifyDataChange()
    }

    protected fun isMovingXY(startX: Float, startY: Float, endX:Float, endY:Float):Boolean{
        if (abs(endX - startX) < 10 && abs(endY - startY) < 10){
            return false
        }
        return true
    }
}