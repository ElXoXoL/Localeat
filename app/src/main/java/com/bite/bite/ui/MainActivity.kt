package com.bite.bite.ui

import android.animation.TimeAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.SystemClock
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import com.bite.bite.R
import com.bite.bite.application.base.BaseActivity
import com.bite.bite.application.extensions.color
import com.bite.bite.application.extensions.drawable
import com.bite.bite.application.extensions.load
import com.bite.bite.application.extensions.loadAnim
import com.bite.bite.ui.map.MapFragment
import com.bite.bite.ui.restaurant.RestaurantFragment
import com.bite.bite.ui.restaurant.menu.MenuManualFragment
import com.bite.bite.utils.AnimType
import com.bite.bite.utils.AnimationUtils
import com.bite.bite.utils.Logger
import com.bite.bite.utils.SwipeDetector
import com.google.android.material.animation.ArgbEvaluatorCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    val viewModel: MainViewModel by viewModel()

    private var lastActionDrawableRes: Int? = null

    private val gestureDetector by lazy { GestureDetector(this, SwipeDetector{
        Logger.log("SWipe")
        if (!viewModel.isCurrentlyScrolling) {
            Logger.log("Swipe active")

            gestureOnBackPressed()
        }
    }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img_toolbar_logo.load(R.drawable.ic_logotipok)
        btn_toolbar_back.load(R.drawable.ic_arrow_back)

        replaceFragmentNoAnim(MapFragment())

        btn_toolbar_back.setOnClickListener {
            onBackPressed()
        }

    }

    // Changes right toolbar btn with animation
    fun changeActionBtn(@DrawableRes newImgRes: Int?){
        if (lastActionDrawableRes == newImgRes) return

        lastActionDrawableRes = newImgRes
        AnimationUtils.hideRightShowRight(btn_toolbar_action){
            btn_toolbar_action.load(newImgRes)
        }
    }

    fun changeActionBtnClick(click: () -> (Unit)){
        btn_toolbar_action.setOnClickListener {
            click()
        }
    }

    var backVisibility: Boolean = false
        set(value) {
            if (value == field) {
                field = value
                return
            }

            if (value){
                AnimationUtils.show(btn_toolbar_back, AnimType.LEFT)
            } else {
                AnimationUtils.hide(btn_toolbar_back, AnimType.LEFT){
                    btn_toolbar_back.visibility = View.GONE
                }
            }

            field = value
        }

    override fun onBackPressed() {
        if (disabledTimeClick != null && SystemClock.elapsedRealtime() < disabledTimeClick!!)
            return

        disabledTimeClick = 400
        when (lastFragment){
            is RestaurantFragment -> {
//                (lastFragment as? RestaurantFragment)?.animateImageBack {
//                    viewModel.cleanRestaurant()
//                    super.onBackPressed()
//                }
                viewModel.cleanRestaurant()
                super.onBackPressed()
            }
            is MapFragment -> {
                val mapFragment = lastFragment as MapFragment
                if (mapFragment.isInfoVisible){
                    mapFragment.isInfoVisible = false
                } else {
                    super.onBackPressed()
                }
            }
            else -> super.onBackPressed()
        }
    }

    private fun gestureOnBackPressed(){
        when (lastFragment){
            is RestaurantFragment, is MenuManualFragment -> onBackPressed()
        }
    }

    fun animateLine(){
        val start = color(R.color.purple)
        val trans = color(R.color.transparent)
        val endBefore = color(R.color.blue)
        val endAfter = color(R.color.yellow)

        val evaluator = ArgbEvaluatorCompat()
        val gradientDrawable = view_toolbar_bottom.background as GradientDrawable

        val animator = TimeAnimator.ofFloat(0.0f, 1.0f)
        animator.duration = 700
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedFraction
            val newArray = intArrayOf(
                start,
                evaluator.evaluate(fraction, endBefore, endAfter)
            )
            gradientDrawable.colors = newArray
        }
        animator.start()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean { // TouchEvent dispatcher.
            if (gestureDetector.onTouchEvent(ev)) {
                // If the gestureDetector handles the event, a swipe has been
                // executed and no more needs to be done.
                return true
            }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}
