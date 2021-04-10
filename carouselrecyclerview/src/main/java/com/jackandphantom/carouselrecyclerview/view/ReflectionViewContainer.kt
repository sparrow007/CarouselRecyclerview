/*
* Copyright 2021 Snehil
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* */

package com.jackandphantom.carouselrecyclerview.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuffXfermode
import android.graphics.Shader
import android.graphics.PorterDuff
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jackandphantom.carouselrecyclerview.R


/**
 * This is an extension of LinearLayout to have reflection of child view in it.
 * To keep the layout simple i have restricted the layout to contain only one child.
 * The purpose of this layout is to have a live reflection of the view in it.
 * If more than one view is present in the layout, it only generates the reflection of the first view.
 * All the draw event are also propagated to the reflection view, so it take twice the time to render. :(
 * */

class ReflectionViewContainer : LinearLayout {

    companion object {
        // Relative height of the view to generate reflection aka Reflection Depth/Length
        private const val DEFAULT_RELATIVE_DEPTH = 0.5f

        // Gap b/w the view and the reflection formed
        // Same effect can be achieved with padding but the distance will be twice
        private const val DEFAULT_GAP = 0.0f
    }

    private lateinit var mReflect: Reflect
    private var mRelativeDepth: Float = DEFAULT_RELATIVE_DEPTH
    private var mReflectionGap: Float = DEFAULT_GAP

    // constructor for programmatically adding view
    constructor(context: Context, view: View) : super(context, null, R.attr.reflect_reflectionLayoutStyle) {
        addView(view)
        mReflect = Reflect(this.context).apply {
            setupView(view, mReflectionGap, mRelativeDepth)
            alpha = 0.85f
        }
        addView(mReflect)
        initLayout(null, R.attr.reflect_reflectionLayoutStyle)
    }

    constructor(context: Context) : super(context, null, R.attr.reflect_reflectionLayoutStyle) {
        initLayout(null, R.attr.reflect_reflectionLayoutStyle)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs,
        R.attr.reflect_reflectionLayoutStyle
    ) {
        initLayout(attrs, R.attr.reflect_reflectionLayoutStyle)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout(attrs, defStyleAttr)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initLayout(attrs, defStyleAttr)
    }

    private fun initLayout(attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.ReflectionViewContainer,
            defStyleAttr,
            0
        )

        mRelativeDepth = a.getFloat(
            R.styleable.ReflectionViewContainer_reflect_relativeDepth,
            mRelativeDepth
        ).coerceAtMost(1.0f)

        mReflectionGap = a.getDimension(
            R.styleable.ReflectionViewContainer_reflect_gap,
            mReflectionGap
        )
        a.recycle()

        // Setting the orientation of linear layout as vertical as reflection view should be
        // just below the main view
        orientation = VERTICAL
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // this guaranties that the reflection is displayed below the main view
        // because this method trigger after the layout inflation is done
        this.getChildAt(0)?.let {
            mReflect = Reflect(this.context).apply {
                setupView(it, mReflectionGap, mRelativeDepth)
                alpha = 0.85f
            }
            addView(mReflect)
        }
    }


    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mReflect.copyMeasuredDimension()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(super.generateDefaultLayoutParams())

    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): LayoutParams {
        return LayoutParams(p)
    }


    class LayoutParams : LinearLayout.LayoutParams {
        private var relativeDepth = DEFAULT_RELATIVE_DEPTH
        private var gap = DEFAULT_GAP

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            val a = c.obtainStyledAttributes(attrs, R.styleable.ReflectionViewContainer_Layout)
            relativeDepth = a.getFloat(
                R.styleable.ReflectionViewContainer_Layout_reflect_relativeDepth,
                DEFAULT_RELATIVE_DEPTH
            )
            gap = a.getDimension(
                R.styleable.ReflectionViewContainer_Layout_reflect_gap,
                DEFAULT_GAP
            )
            a.recycle()
        }

        /**
         * {@inheritDoc}
         */
        constructor(source: ViewGroup.LayoutParams) : super(source)
    }

    internal class Reflect @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {

        private lateinit var mMode: PorterDuffXfermode
        private lateinit var toReflect: View
        private var mGap = 0f
        private var mRelDepth = 0f
        private val mPaint = Paint()
        private lateinit var mShader: LinearGradient

        fun setupView(view: View, gap: Float, relDepth: Float) {
            toReflect = view
            mGap = gap
            mRelDepth = relDepth
        }

        @SuppressLint("DrawAllocation")
        override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
            super.onLayout(changed, left, top, right, bottom)
            // I didn't find any way around this draw allocation as it required the size of the view
            mShader = LinearGradient(
                0f,
                height/2f, 0f, height.toFloat(),  0x7FFFFFFF, 0x00000000,
                Shader.TileMode.CLAMP
            )
            mPaint.shader = mShader
            mPaint.xfermode = mMode
        }

        fun copyMeasuredDimension() {

            val p = layoutParams
            val q = toReflect.layoutParams
            /** [ MATCHING REFLECTION VIEW DIMENSIONS WITH THE MAIN VIEW ] */
            toReflect.measure(MeasureSpec.makeMeasureSpec(resources.displayMetrics.widthPixels, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(resources.displayMetrics.heightPixels, MeasureSpec.AT_MOST))
            val msWidth = toReflect.measuredWidth
            val msHeight = toReflect.measuredHeight

            when {
                q.width >= 0 -> {
                    p.width = q.width
                }
                else -> {
                    p.width = msWidth
                }
            }

            when {
                q.height >= 0 -> {
                    p.height = (mRelDepth * q.height + mGap + toReflect.paddingBottom).toInt()
                }
                else -> {
                    p.height =  (mRelDepth *  msHeight).toInt()
                }
            }

            this.setMeasuredDimension(toReflect.measuredWidth, (mRelDepth *  toReflect.measuredHeight).toInt())
            layoutParams = p

            /** END */
            mMode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            if (this::toReflect.isInitialized) {
                canvas.save()
                val cx = width / 2f
                val cy = height / 2f
                // rotating, flipping and translating the canvas to create the required mirror image.
                canvas.rotate(-180f, cx, cy)
                canvas.scale(-1f, 1f, cx, cy)

                val gap =  height - mRelDepth * toReflect.height
                val mTranslateY = toReflect.height - height + gap

                canvas.translate(0f, -mTranslateY)
                canvas.clipRect(left, top, width, height)
                toReflect.draw(canvas)
                canvas.restore()
                canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(),  mPaint)
            }
        }
    }
}