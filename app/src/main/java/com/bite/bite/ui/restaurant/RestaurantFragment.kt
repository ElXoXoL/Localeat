package com.bite.bite.ui.restaurant

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.bite.bite.R
import com.bite.bite.application.base.BaseFragment
import com.bite.bite.application.delayFunc
import com.bite.bite.application.extensions.load
import com.bite.bite.application.extensions.loadBitmap
import com.bite.bite.application.runOnMain
import com.bite.bite.application.runOnWorker
import com.bite.bite.models.RestaurantObj
import com.bite.bite.ui.MainActivity
import com.bite.bite.ui.MainViewModel
import com.bite.bite.ui.contacts.DialogContacts
import com.bite.bite.ui.restaurant.menu.MenuManualFragment
import com.bite.bite.utils.AnimType
import com.bite.bite.utils.AnimationUtils
import com.bite.bite.utils.TransitionUtils
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RestaurantFragment: BaseFragment(R.layout.fragment_restaurant){

    private val viewModel: MainViewModel by sharedViewModel()

    private val mainActivity: MainActivity?
        get() = activity as MainActivity?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity?.changeActionBtn(R.drawable.ic_phone)
        mainActivity?.changeActionBtnClick {
            DialogContacts(mainActivity!!, viewModel.contacts.value!!)
        }

        mainActivity?.backVisibility = true

        card_rest_container.transitionName = viewModel.transitionElem?.name
        setClicks()

        setRestaurantData(viewModel.selectedRestaurant.value)
        playOpenAnimations()

    }

    private fun playOpenAnimations(){

        delayFunc(300) {
            AnimationUtils.show(btn_favourite, AnimType.BOTTOM)
            AnimationUtils.show(btn_open_map, AnimType.BOTTOM)
            AnimationUtils.show(btn_open_menu, AnimType.BOTTOM)
        }
    }

    private fun setClicks(){
        btn_open_menu.setOnClickListener {
            val fragment = MenuManualFragment().apply {
                enterTransition = TransitionUtils.slide
                this@RestaurantFragment.exitTransition = TransitionUtils.fadeLinear
            }
            replaceFragmentNoAnim(fragment)
        }
    }

    private fun setRestaurantData(item: RestaurantObj?){
        if (item == null) return

//        runOnWorker {
//            item.bitmap = item.bitmap ?: loadBitmap(item.restaurant.img)
//            runOnMain {
//                img_rest_photo.load(item.bitmap)
//            }
//        }
        img_rest_photo.load(item.restaurant.img)

        tv_rest_description.text = item.restaurant.description
        tv_rest_name.text = item.restaurant.name

//        Handler().postDelayed({
//            animateImage()
//        }, 150)
    }

    private fun animateImage(){
        if (img_rest_photo == null)
            return

        val height = img_rest_photo.measuredHeight
        val anim = ValueAnimator.ofInt(height, height * 2)

        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams? = img_rest_photo?.layoutParams
            layoutParams?.height = value
            img_rest_photo?.layoutParams = layoutParams
            img_rest_photo?.load(viewModel.selectedRestaurant.value?.bitmap)
        }
        anim.duration = 500
        anim.start()
    }

    fun animateImageBack(onFinished: () -> (Unit)){
        if (img_rest_photo == null)
            return

        val height = img_rest_photo.measuredHeight
        val anim = ValueAnimator.ofInt(height, height / 2)

        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams? = img_rest_photo?.layoutParams
            layoutParams?.height = value
            img_rest_photo?.layoutParams = layoutParams
            img_rest_photo?.load(viewModel.selectedRestaurant.value?.bitmap)
        }
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) { // done
                onFinished()
            }
        })
        anim.duration = 300
        anim.start()
    }

}