package com.bite.bite.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeAnimator
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator

import android.view.animation.ScaleAnimation
import androidx.cardview.widget.CardView
import com.bite.bite.R
import com.bite.bite.models.ColorSet
import com.google.android.material.animation.ArgbEvaluatorCompat


enum class AnimType{
    TOP,
    BOTTOM,
    RIGHT,
    LEFT
}

object AnimationUtils{

    fun circularReveal(x: Int, y: Int, radius: Float, view: CardView){

        val anim = ViewAnimationUtils.createCircularReveal(view, x, y, 0f, radius)
        anim.duration = 500

        // make the view visible and start the animation
        view.visibility = View.VISIBLE
        anim.start()
    }

    fun circularHide(x: Int, y: Int, radius: Float, view: View, onHideAction: () -> Unit){

        val anim = ViewAnimationUtils.createCircularReveal(view, x, y, radius, 0f)

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                view.visibility = View.GONE
                onHideAction()
            }
        })

        // start the animation
        anim.start()
    }

    fun scaleAnimation(startX: Float, endX: Float, startY: Float, endY: Float, view: View){
        val anim: Animation = ScaleAnimation(
            startX, endX,  // Start and end values for the X axis scaling
            startY, endY,  // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 0.5f
        ) // Pivot point of Y scaling
        anim.interpolator = DecelerateInterpolator()
        anim.fillAfter = true // Needed to keep the result of the animation

        anim.duration = 300
        view.startAnimation(anim)
    }

    fun animateGradientChange(start: ColorSet, end: ColorSet, duration: Long, view: View){

        try {
            val evaluator = ArgbEvaluatorCompat()
            val gradientDrawable = view.background as GradientDrawable

            val animator = TimeAnimator.ofFloat(0.0f, 1.0f)
            animator.duration = duration
            animator.addUpdateListener { valueAnimator ->
                val newArray = intArrayOf(
                    evaluator.evaluate(valueAnimator.animatedFraction, start.before, start.after),
                    evaluator.evaluate(valueAnimator.animatedFraction, end.before, end.after)
                )
                gradientDrawable.colors = newArray
            }
            animator.start()
        } catch (e: Exception){
            Logger.log(e)
        }
    }

    // Function for changing menu items
    fun change(view: View, isFromRight: Boolean, changeFun: () -> (Unit)){
        if (isFromRight) hideLeftShowRight(view, changeFun) else hideRightShowLeft(view, changeFun)
    }

    private fun hideLeftShowRight(view: View, changeFun: () -> (Unit)){
        val animationHide = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_to_left_menu)
        val animationShow = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_right_menu)
        animationHide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                changeFun()
                view.startAnimation(animationShow)
            }
        })
        view.startAnimation(animationHide)
    }

    private fun hideRightShowLeft(view: View, changeFun: () -> (Unit)){
        val animationHide = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_to_right_menu)
        val animationShow = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_left_menu)
        animationHide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                changeFun()
                view.startAnimation(animationShow)
            }
        })
        view.startAnimation(animationHide)
    }

    // Function mostly for toolbar right btn change animation
    fun hideRightShowRight(view: View, changeFun: () -> (Unit)){
        val animationHide = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_to_right_menu)
        val animationShow = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_right_menu)
        animationHide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                changeFun()
                view.startAnimation(animationShow)
            }
        })
        view.startAnimation(animationHide)
    }

    fun hide(view: View, animType: AnimType, onHideAction: () -> Unit){
        when (animType){
            AnimType.TOP -> hideTop(view, onHideAction)
            AnimType.BOTTOM -> hideBottom(view, onHideAction)
            AnimType.RIGHT -> hideRight(view, onHideAction)
            AnimType.LEFT -> hideLeft(view, onHideAction)
        }
    }

    private fun hideBottom(view: View, onHideAction: () -> Unit){
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_bottom)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                onHideAction()
            }
        })
        view.startAnimation(animation)
    }

    private fun hideRight(view: View, onHideAction: () -> Unit){
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_to_right)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                onHideAction()
            }
        })
        view.startAnimation(animation)
    }

    private fun hideTop(view: View, onHideAction: () -> Unit){
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_top)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                onHideAction()
            }
        })
        view.startAnimation(animation)
    }

    private fun hideLeft(view: View, onHideAction: () -> Unit){
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_to_left)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                onHideAction()
            }
        })
        view.startAnimation(animation)
    }

    fun show(view: View, animType: AnimType){
        when (animType){
            AnimType.TOP -> showTop(view)
            AnimType.BOTTOM -> showBottom(view)
            AnimType.RIGHT -> showRight(view)
            AnimType.LEFT -> showLeft(view)
        }
    }

    private fun showBottom(view: View){
        val animationShow = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_bottom)
        view.visibility = View.VISIBLE
        view.startAnimation(animationShow)
    }

    private fun showRight(view: View){
        val animationShow = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_right)
        view.visibility = View.VISIBLE
        view.startAnimation(animationShow)
    }

    private fun showTop(view: View){
        val animationShow = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_top)
        view.visibility = View.VISIBLE
        view.startAnimation(animationShow)
    }

    private fun showLeft(view: View){
        val animationShow = AnimationUtils.loadAnimation(view.context, R.anim.item_anim_fall_from_left)
        view.visibility = View.VISIBLE
        view.startAnimation(animationShow)
    }
}