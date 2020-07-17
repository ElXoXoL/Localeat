package com.bite.bite.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bite.bite.R
import com.bite.bite.models.TransitionElem

class FragmentUtils(private val activity: AppCompatActivity) {

    // Last fragment loaded
    var lastFragment: Fragment? = null
    
    private val supportFragmentManager: FragmentManager
        get() = activity.supportFragmentManager

    private val fragmentCount: Int
        get() = supportFragmentManager.backStackEntryCount

    private fun getLastFragmentInStack(): Fragment?{
        if (fragmentCount <= 1){
            return null
        }
        val index = supportFragmentManager.backStackEntryCount - 2
        val backEntry = supportFragmentManager.getBackStackEntryAt(index)
        val tag = backEntry.name
        return supportFragmentManager.findFragmentByTag(tag)
    }
    
    private fun popBackStack(){
        supportFragmentManager.popBackStack()
    }

    // Should be called in base activity onBackPressed for fragment counting
    fun onBackPressed(){
        // If only 1 fragment in stack - pop it to be able to close activity on backPress
        if (fragmentCount == 1){
            popBackStack()
        }
        lastFragment = getLastFragmentInStack()
    }

    // Replace fragment with animation but without pop animation
    fun replaceFragment(fragment: Fragment) {
        if (lastFragment != null
            && fragment::class.java.name == lastFragment!!::class.java.name){
            return
        }
        lastFragment = fragment

        val fragmentTag = fragment::class.java.name

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.right_in_transition, R.anim.left_out_transition)
            .replace(R.id.fragment_container, fragment, fragment::class.java.name)
            .addToBackStack(fragmentTag)
            .commitAllowingStateLoss()
    }

    // Replaces fragment with animation for popping
    fun replaceFragmentWithPopAnim(fragment: Fragment) {
        if (lastFragment != null
            && fragment::class.java.name == lastFragment!!::class.java.name){
            return
        }
        lastFragment = fragment

        val fragmentTag = fragment::class.java.name

        if (supportFragmentManager.findFragmentByTag(fragmentTag) != null){
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_in_transition, R.anim.left_out_transition, R.anim.left_in_transition, R.anim.right_out_transition)
                .replace(R.id.fragment_container, fragment, fragment::class.java.name)
                .commitAllowingStateLoss()
        } else {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_in_transition, R.anim.left_out_transition, R.anim.left_in_transition, R.anim.right_out_transition)
                .replace(R.id.fragment_container, fragment, fragment::class.java.name)
                .addToBackStack(fragment::class.java.name)
                .commitAllowingStateLoss()
        }

    }

    // Replaces fragment without animation
    fun replaceFragmentNoAnim(fragment: Fragment) {
        if (lastFragment != null
            && fragment::class.java.name == lastFragment!!::class.java.name){
            return
        }
        lastFragment = fragment

        val fragmentTag = fragment::class.java.name

        if (supportFragmentManager.findFragmentByTag(fragmentTag) != null){
            replaceFragmentNoAnimNoStack(fragment)
            return
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment::class.java.name)
            .addToBackStack(fragment::class.java.name)
            .commitAllowingStateLoss()
    }

    // Replaces fragment with transition elems
    fun replaceFragmentTransition(fragment: Fragment, elem: TransitionElem) {
        if (lastFragment != null
            && fragment::class.java.name == lastFragment!!::class.java.name){
            return
        }
        lastFragment = fragment

        supportFragmentManager.beginTransaction()
            .addSharedElement(elem.elem, elem.name)
            .replace(R.id.fragment_container, fragment, fragment::class.java.name)
            .addToBackStack(fragment::class.java.name)
            .commitAllowingStateLoss()
    }

    // Replaces fragment without animation and stacking
    fun replaceFragmentNoAnimNoStack(fragment: Fragment) {
        if (lastFragment != null
            && fragment::class.java.name == lastFragment!!::class.java.name){
            return
        }
        lastFragment = fragment

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment::class.java.name)
            .commitAllowingStateLoss()
    }
}