package com.bite.bite.ui.map

import android.animation.ArgbEvaluator
import android.graphics.drawable.GradientDrawable
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bite.bite.R
import com.bite.bite.application.base.BaseFragment
import com.bite.bite.application.delayFunc
import com.bite.bite.application.extensions.*
import com.bite.bite.koin.KoinComponents
import com.bite.bite.models.FoodType
import com.bite.bite.models.RestaurantObj
import com.bite.bite.models.Sales
import com.bite.bite.ui.MainActivity
import com.bite.bite.ui.MainViewModel
import com.bite.bite.ui.list.ListFragment
import com.bite.bite.ui.restaurant.RestaurantFragment
import com.bite.bite.utils.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.abs


class MapFragment : BaseFragment(R.layout.fragment_map),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    DiscreteScrollView.ScrollStateChangeListener<AdapterSales.ViewHolder>,
    DiscreteScrollView.OnItemChangedListener<AdapterSales.ViewHolder>{

    private var mMap: GoogleMap? = null

    private val viewModel: MainViewModel by sharedViewModel()

    private var lastLocation: Location? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val mapUtils by lazy { MapUtils(context!!) }

    private val evaluator by lazy { ArgbEvaluator() }
    private var ovalBg: GradientDrawable? = null
    private val blue by lazy { context!!.color(R.color.blue) }
    private val purple by lazy { context!!.color(R.color.purple) }

    var isInfoVisible = false
        set(value) {
            Logger.log(value)
            if (field == value)
                return

            if (value) {
                AnimationUtils.translateFromTop(cs_info)
                mainActivity?.backVisibility = true
            } else {
                AnimationUtils.translateToTop(cs_info)
                mainActivity?.backVisibility = false
            }

            field = value
        }

    private val mainActivity: MainActivity?
        get() = activity as MainActivity?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ovalBg = view_oval.background as GradientDrawable
        ovalBg?.setColor(purple)

        mainActivity?.changeActionBtnClick {
            isInfoVisible = true
        }
        mainActivity?.changeActionBtn(R.drawable.ic_info)
        this.exitTransition = null

        initMap()
        enableLocation()

        btn_open_list.setOnClickListener {
            replaceFragmentWithPopAnim(ListFragment())
        }

        btn_my_location.setOnClickListener {
            getLastLocation(true)
        }

        playOpenAnimations()

        mainActivity?.backVisibility = false

        viewModel.restaurantList.observe(viewLifecycleOwner, Observer {
            setMarkers(it)
        })

        viewModel.foodTypes.observe(viewLifecycleOwner, Observer {
            setFoodTypes(it)
        })

        viewModel.sales.observe(viewLifecycleOwner, Observer {
            setSales(it)
        })

        viewModel.selectedRestaurant.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                tv_info_contacts_label.visibility = View.INVISIBLE
                tv_info_descr.visibility = View.INVISIBLE
                AnimationUtils.translateFromTop(cs_info){
                    val fragment = RestaurantFragment().apply {
                        enterTransition = TransitionUtils.slide
                        this@MapFragment.exitTransition = TransitionUtils.fadeLinear
                    }
                    replaceFragmentNoAnim(fragment)
                }
            }
        })
    }

    private fun playOpenAnimations(){

        delayFunc(500) {
            AnimationUtils.show(btn_my_location, AnimType.RIGHT)
            AnimationUtils.show(btn_open_list, AnimType.RIGHT)
            AnimationUtils.show(cs_oval, AnimType.BOTTOM)
            AnimationUtils.show(rec_map_sales, AnimType.BOTTOM)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap?.uiSettings?.isCompassEnabled = true
        mMap?.uiSettings?.isMyLocationButtonEnabled = false

        mMap?.setOnMarkerClickListener(this)

        mMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.style))

        if (KoinComponents.locationUtils.isPermissionGranted(activity)) {
            mMap?.isMyLocationEnabled = true
            buildGoogleApiClient()
            getLastLocation{
                zoomBetweenMarkers()
            }
        }
//        val kyivLatLng = LatLng(50.45466, 30.5238)
//        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(kyivLatLng, LocationUtils.ZOOM_DEFAULT))

        // Add a marker in Sydney and move the camera\
    }

    private fun initMap(){
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun buildGoogleApiClient() {
        if (activity == null || !KoinComponents.locationUtils.isPermissionGranted(activity)) return
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
    }

    private fun getLastLocation(isUserRequest: Boolean = false, onFailure: () -> (Unit) = {}){
        if (activity == null || !KoinComponents.locationUtils.isPermissionGranted(activity)) return
        fusedLocationClient?.lastLocation?.addOnSuccessListener(activity!!) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    currentLatLng,
                    if (isUserRequest) LocationUtils.ZOOM_DEFAULT_USER else LocationUtils.ZOOM_DEFAULT))
            }
        }?.addOnFailureListener {
            onFailure()
        }
    }

    // Checking and requesting location permission
    private fun enableLocation(){
        if (!KoinComponents.locationUtils.isPermissionGranted(activity)){
            KoinComponents.locationUtils.requestPermission(activity)
        }
    }

    // Clearing all markers and adding new
    private fun setMarkers(restaurants: MutableList<RestaurantObj>?){
        Logger.log("setMarkers")
        if (restaurants == null) return

        mMap?.clear()

        var count = 0
        val markers: MutableList<Marker> = mutableListOf()

        restaurants.forEach {
            val options = MarkerOptions()
                .position(LatLng(it.restaurant.lat.toDouble(), it.restaurant.lng.toDouble()))
                .title(it.restaurant.name)
                .icon(mapUtils.markerIconDefault)

            val marker = mMap?.addMarker(options)!!
            marker.tag = count++
            marker.showInfoWindow()

            markers.add(marker)
        }

        viewModel.markerList.clear()
        viewModel.markerList.addAll(markers)

        zoomBetweenMarkers()
    }

    private fun setFoodTypes(list: MutableList<FoodType>?){
        Logger.log("setFoodTypes")
        if (list == null) return

        rec_map_food_types.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterFoodTypes = AdapterFoodTypes{
            rec_map_food_types.toPos(it)
        }
        rec_map_food_types.adapter = adapterFoodTypes

        list.forEach {
            adapterFoodTypes.addElem(it)
        }
//        rec_map_food_types.scheduleLayoutAnimation()
    }

    private fun setSales(list: MutableList<RestaurantObj>?){
        Logger.log("setFoodTypes")
        if (list == null) return

//        rec_map_sales.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterSales = AdapterSales{
            rec_map_sales.smoothScrollToPosition(it)
            viewModel.selectRestaurant(it)
        }
        rec_map_sales.adapter = adapterSales
        rec_map_sales.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setPivotY(Pivot.Y.BOTTOM)
                .build()
        )
        rec_map_sales.setItemTransitionTimeMillis(300)
        rec_map_sales.setOffscreenItems(list.size * 180.px) //Reserve extra space equal to (childSize * count) on each side of the view
        rec_map_sales.addScrollStateChangeListener(this)
        rec_map_sales.addOnItemChangedListener(this)


        list.forEach {
            adapterSales.addElem(it)
        }


//        rec_map_sales.scheduleLayoutAnimation()
    }

    private fun zoomBetweenMarkers(){
        val markers = viewModel.markerList
        if (markers.isNullOrEmpty()) return

        val builder = LatLngBounds.Builder()
        markers.forEach {
            builder.include(it.position)
        }

        val bounds = builder.build()
        val padding = 30.px // Padding from view edges

        try {
            mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        } catch (e: Exception){
            Logger.log(e)
        }
    }

    private fun getMarkerXY(marker: Marker?){
        if (marker == null) return
        mMap?.projection?.toScreenLocation(marker.position).toString()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val pos = marker.tag as Int

        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(
            marker.position,
            LocationUtils.ZOOM_DEFAULT_USER))

        viewModel.selectRestaurant(pos)
//        getMarkerXY(marker)
        return true
    }

    override fun onScroll(
        currentPosition: Float,
        currentIndex: Int, newIndex: Int,
        currentHolder: AdapterSales.ViewHolder?,
        newCurrent: AdapterSales.ViewHolder?
    ) {
        changeOvalColor(abs(currentPosition), (currentHolder?.adapterPosition ?: 0) % 2)
    }

    override fun onScrollStart(p0: AdapterSales.ViewHolder, p1: Int) {}
    override fun onScrollEnd(p0: AdapterSales.ViewHolder, p1: Int) {}

    override fun onCurrentItemChanged(viewHolder: AdapterSales.ViewHolder?, adapterPosition: Int) { //viewHolder will never be null, because we never remove items from adapter's list
    }

    private fun changeOvalColor(position: Float, holderPos: Int){
        val startColor = if (holderPos == 0) purple else blue
        val endColor = if (holderPos == 0) blue else purple

        val color = interpolate(position, startColor, endColor)
        ovalBg?.setColor(color)
    }

    private fun interpolate(fraction: Float, c1: Int, c2: Int): Int {
        return evaluator.evaluate(fraction, c1, c2) as Int
    }
}
