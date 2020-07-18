package com.bite.bite.application.base

import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bite.bite.models.TransitionElem
import com.bite.bite.utils.FragmentUtils
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseActivity: AppCompatActivity() {

    private val fragmentUtils: FragmentUtils by inject { parametersOf(this)}

    // Disable on back press click for some millis
    // It sets assigned value plus current system time
    var disabledTimeClick: Long? = null
        set(value) {
            field = if (value != null) value + SystemClock.elapsedRealtime() else null
        }

    val lastFragment: Fragment?
        get() = fragmentUtils.lastFragment

    val fragmentCount: Int
        get() = fragmentUtils.fragmentCount

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

}