package com.bite.bite.application.base

import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bite.bite.models.TransitionElem
import com.bite.bite.utils.FragmentUtils

abstract class BaseActivity: AppCompatActivity() {

    private val fragmentUtils by lazy { FragmentUtils(this) }

    var disabledTimeClick: Long? = null
        set(value) {
            field = if (value != null) value + SystemClock.elapsedRealtime() else null
        }

    override fun onBackPressed() {
        fragmentUtils.onBackPressed()
        super.onBackPressed()
    }

    fun replaceFragment(fragment: Fragment){
        fragmentUtils.replaceFragment(fragment)
    }

    fun replaceFragmentWithPopAnim(fragment: Fragment){
        fragmentUtils.replaceFragmentWithPopAnim(fragment)
    }

    fun replaceFragmentNoAnim(fragment: Fragment){
        fragmentUtils.replaceFragmentNoAnim(fragment)
    }

    fun replaceFragmentTransition(fragment: Fragment, elem: TransitionElem){
        fragmentUtils.replaceFragmentTransition(fragment, elem)
    }

    fun replaceFragmentNoAnimNoStack(fragment: Fragment){
        fragmentUtils.replaceFragmentNoAnimNoStack(fragment)
    }

    val lastFragment: Fragment?
        get() = fragmentUtils.lastFragment

}