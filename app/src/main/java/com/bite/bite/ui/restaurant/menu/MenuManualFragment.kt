package com.bite.bite.ui.restaurant.menu

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bite.bite.R
import com.bite.bite.application.base.BaseFragment
import com.bite.bite.application.extensions.toPos
import com.bite.bite.models.FoodItem
import com.bite.bite.models.FoodType
import com.bite.bite.ui.MainViewModel
import com.bite.bite.utils.AnimationUtils
import com.bite.bite.utils.Logger
import kotlinx.android.synthetic.main.fragment_menu_manual.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MenuManualFragment: BaseFragment(R.layout.fragment_menu_manual){

    private val viewModel: MainViewModel by sharedViewModel()
    private val adapterFoodItems by lazy { AdapterFoodItems() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.defaultFoodType()

        viewModel.foodTypes.observe(viewLifecycleOwner, Observer {
            setFoodTypes(it)
        })

        viewModel.foodItems.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setupFoodItems()
                viewModel.selectFoodType(0)
            }
        })

        viewModel.selectedFoodType.observe(viewLifecycleOwner, Observer { foodType ->
            setFoodItems(
//                viewModel.foodItems.value?.filter { it.id == foodType.id }?.toMutableList(),
                viewModel.foodItems.value,
                foodType
            )
        })
    }

    private fun setFoodTypes(list: MutableList<FoodType>?){
        Logger.log("setFoodTypes")
        if (list == null) return

        rec_menu_categories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterFoodCategories = AdapterFoodCategories{pos ->
            rec_menu_categories.toPos(pos)
            viewModel.selectFoodType(pos)
        }
        rec_menu_categories.adapter = adapterFoodCategories
        list.forEach {
            adapterFoodCategories.addElem(it)
        }

    }

    private fun setupFoodItems(){
        rec_menu_items.layoutManager = LinearLayoutManager(context)
        rec_menu_items.adapter = adapterFoodItems
        rec_menu_items.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            // We disable close on swipe when touched recycler and enable when touch is end

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> { // Do something
                        Logger.log("SCROLL_STATE_TOUCH_SCROLL")
                        viewModel.isCurrentlyScrolling = true
                    }
                    AbsListView.OnScrollListener.SCROLL_STATE_IDLE -> {
                        Logger.log("SCROLL_STATE_IDLE")
                        viewModel.isCurrentlyScrolling = false
                    }
                    else -> { // Do something
                    }
                }
            }
        })
    }

    private fun setFoodItems(list: MutableList<FoodItem>?, foodType: FoodType){
        Logger.log("setFoodItems")
        if (list == null) return

        if (viewModel.isFromRight != null){

            AnimationUtils.change(cons_food_items, viewModel.isFromRight!!){
                tv_menu_category.text = foodType.type
                adapterFoodItems.clear()
                list.forEach {
                    adapterFoodItems.addElem(it)
                }
            }
        } else {
            tv_menu_category.text = foodType.type
            adapterFoodItems.clear()
            list.forEach {
                adapterFoodItems.addElem(it)
            }
        }
    }
}