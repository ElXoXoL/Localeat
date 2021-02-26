package com.bite.bite.ui.restaurant

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bite.bite.R
import com.bite.bite.application.base.BaseFragment
import com.bite.bite.application.delayFunc
import com.bite.bite.application.extensions.load
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
import org.koin.androidx.viewmodel.ext.android.viewModel


class RestaurantFragment: BaseFragment(R.layout.fragment_restaurant){

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: RestaurantViewModel by viewModel()

    private val mainActivity: MainActivity?
        get() = activity as MainActivity?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity?.backVisibility = true

        setActionBtn()

        setClicks()

        viewModel.selectedRestaurant.observe(viewLifecycleOwner, Observer {
            setRestaurantData(it)
        })

        playOpenAnimations()

        viewModel.selectedRestaurant.postValue(mainViewModel.selectedRestaurant.value)
    }

    private fun setActionBtn(){
//        mainActivity?.changeActionBtn(R.drawable.ic_phone)
//        mainActivity?.changeActionBtnClick {
//            DialogContacts(mainActivity!!, viewModel.contacts.value!!)
//        }
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
                viewModel = this@RestaurantFragment.viewModel
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

    private fun animateImageBack(onFinished: () -> (Unit)){
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