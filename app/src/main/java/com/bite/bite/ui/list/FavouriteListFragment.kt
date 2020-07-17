package com.bite.bite.ui.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bite.bite.R
import com.bite.bite.application.base.BaseFragment
import com.bite.bite.application.extensions.toPos
import com.bite.bite.application.extensions.toast
import com.bite.bite.models.FoodType
import com.bite.bite.models.RestaurantObj
import com.bite.bite.ui.MainActivity
import com.bite.bite.ui.MainViewModel
import com.bite.bite.ui.map.AdapterFoodTypes
import com.bite.bite.ui.restaurant.RestaurantFragment
import com.bite.bite.utils.Logger
import com.bite.bite.utils.TransitionUtils
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FavouriteListFragment : BaseFragment(R.layout.fragment_list) {

    private val viewModel: MainViewModel by sharedViewModel()

    private val mainActivity: MainActivity?
        get() = activity as MainActivity?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity?.changeActionBtn(R.drawable.ic_star_white)
        mainActivity?.changeActionBtnClick {
            mainActivity?.onBackPressed()
        }

        mainActivity?.backVisibility = true

        viewModel.restaurantList.observe(viewLifecycleOwner, Observer {
            setList(it)
        })

        viewModel.foodTypes.observe(viewLifecycleOwner, Observer {
            setFoodTypes(it)
        })

        viewModel.selectedRestaurant.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val fragment = RestaurantFragment().apply {
                    enterTransition = TransitionUtils.slide
                    this@FavouriteListFragment.exitTransition = TransitionUtils.fadeLinear
                }

                replaceFragmentNoAnim(fragment)
            }
        })
    }

    // Clearing all markers and adding new
    private fun setList(restaurants: MutableList<RestaurantObj>?){
        Logger.log("setList")
        if (restaurants == null) return

        rec_rest_list.layoutManager = LinearLayoutManager(context)
        val adapterRestaurants = AdapterRestaurants{ pos, elem ->
            viewModel.transitionElem = elem
            rec_rest_list.toPos(pos)
            viewModel.selectRestaurant(pos)
        }

        rec_rest_list.adapter = adapterRestaurants
        restaurants.forEach {
            adapterRestaurants.addElem(it)
        }
    }

    private fun setFoodTypes(list: MutableList<FoodType>?){
        Logger.log("setFoodTypes")
        if (list == null) return

        rec_list_food_types.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterFoodTypes = AdapterFoodTypes{
            rec_list_food_types.toPos(it)
        }
        rec_list_food_types.adapter = adapterFoodTypes

        list.forEach {
            adapterFoodTypes.addElem(it)
        }
    }
}
