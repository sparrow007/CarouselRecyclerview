package com.jackandphantom.carouselrecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class CarouselRecyclerview(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    /** Create layout manager builder so that we can easily add more methods to it */
    private var carouselLayoutManagerBuilder: CarouselLayoutManager.Builder =
        CarouselLayoutManager.Builder()

    /**
     * Initialize the layout manager and also enable the childDrawingOrder
     */
    init {
        layoutManager = carouselLayoutManagerBuilder.build()
        isChildrenDrawingOrderEnabled = true
    }

    /**
     * set the 3d item to the layout manager
     * @param is3DItem make items in layout manager tilt if true
     * */
    fun set3DItem(is3DItem: Boolean) {
        carouselLayoutManagerBuilder.set3DItem(is3DItem)
        layoutManager = carouselLayoutManagerBuilder.build()
    }

    /**
     * set the infinite items in the layout manager
     * @param isInfinite make loop of items
     * */
    fun setInfinite(isInfinite: Boolean) {
        carouselLayoutManagerBuilder.setIsInfinite(isInfinite)
        layoutManager = carouselLayoutManagerBuilder.build()
    }

    /**
     * set flat items in layout manager
     * @param isFlat flat the views and also increase the interval between views
     * */
    fun setFlat(isFlat: Boolean) {
        carouselLayoutManagerBuilder.setIsFlat(isFlat)
        layoutManager = carouselLayoutManagerBuilder.build()
    }

    /**
     * set the alpha for each item depends on the position in the layout manager
     * @param isAlpha alpha value and it should in range (0.3f - 1.0f)
     */
    fun setAlpha(isAlpha: Boolean) {
        carouselLayoutManagerBuilder.setIsAlpha(true)
        layoutManager = carouselLayoutManagerBuilder.build()
    }

    /**
     * set the interval ratio which is gap between items (views) in layout manager
     * @param ratio value of gap, it should in range (0.4f - 1f)
     */
    fun setIntervalRatio(ratio: Float) {
        carouselLayoutManagerBuilder.setIntervalRatio(ratio)
        layoutManager = carouselLayoutManagerBuilder.build()
    }

    /**
     * Get the layout manager instance
     * @return CoverLayout
     */
     fun getCarouselLayoutManager(): CarouselLayoutManager {
        return layoutManager as CarouselLayoutManager
    }

    /**
     * provides the drawing of the child view in the layout manager, calculate the priority of the views
     * depends on the position in the layout manager
     * @param childCount currently number of visible view
     * @param i current position of view
     * @return order of the view
     */
    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        val center: Int = getCarouselLayoutManager().centerPosition()

        // Get the actual position of the i-th child view in RecyclerView

        // Get the actual position of the i-th child view in RecyclerView
        val actualPos: Int = getCarouselLayoutManager().getChildActualPos(i)

        // The number of intervals from the middle item

        // The number of intervals from the middle item
        val dist = actualPos - center

        var order: Int
        // [< 0] indicates that the item is located to the left of the middle item and can be drawn in order
        // [< 0] indicates that the item is located to the left of the middle item and can be drawn in order
        order = if (dist < 0) {
            i
        } else {
            //[>= 0] It means that the item is located to the right
            // of the middle item, and the order needs to be reversed.
            childCount - 1 - dist
        }

        if (order < 0) order = 0 else if (order > childCount - 1) order = childCount - 1

        return order
    }

    fun setItemSelectListener(listener: CarouselLayoutManager.OnSelected) {
        getCarouselLayoutManager().setOnSelectedListener(listener)
    }

    /**
     * Get selected position from the layout manager
     * @return center view of the layout manager
     */
    fun getSelectedPosition() = getCarouselLayoutManager().getSelectedPosition()

}