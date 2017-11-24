package com.example.dima.taxiservice.Screens.OrderScreen

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import com.example.dima.taxiservice.MapUtils.AddressAdapter
import com.example.dima.taxiservice.MapUtils.LocationFinder
import com.example.dima.taxiservice.R
import com.example.dima.taxiservice.Screens.UserScreen.UserPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*

/**
 * Created by Dima on 24.11.2017.
 */
class OrderView: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var tvCur: TextView
    private lateinit var tvTar: TextView
    private lateinit var tvCost: TextView
    private lateinit var tvTime: TextView


    private fun init(){
        tvCur=findViewById(R.id.tvFrom)
        tvTar=findViewById(R.id.tvTo)
        tvCost=findViewById(R.id.tvCost)
        tvTime=findViewById(R.id.tvAtTime)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_layout)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapOrder) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isScrollGesturesEnabled=false
        mMap.uiSettings.isZoomGesturesEnabled=false
//        buildPolyline(List(2,{index -> LatLng(index*455f.toDouble(),index*32f.toDouble()) }))
        TestInit()
    }

    fun TestInit(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val geocoder = Geocoder(this, Locale.getDefault())
        val addressAdapter = AddressAdapter( geocoder, object :AddressAdapter.AddressListener{
            override fun onAddressFinded(location: LatLng?, address: Address?, request: Int) {
                if(location!=null&&address!=null) {
                    mMap.addMarker(MarkerOptions().position(location).title(address.featureName))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
                }
            }
        })
        val locationFinder = LocationFinder(object :LocationFinder.LocationFinderListener{
            override fun onSatusChange(s: String?) {
            }

            override fun onLocationFinded(location: Location?) {
                location?.let {
                    addressAdapter.findAddress(LatLng(location.latitude,location.longitude),0)
                }
            }

        }, locationManager, UserPresenter.curAddressRequest)
        locationFinder.startFinding(object :LocationFinder.Ifinder{
            override fun getContext(): Context {
                return this@OrderView
            }
            override fun requestPermissions(vararg permission: String?) {
            }
        })
    }

    fun buildPolyline(mPoints:List<LatLng>){
        val line = PolylineOptions()
        line.width(4f).color(R.color.material_deep_teal_200)
        val latLngBuilder = LatLngBounds.Builder()
        for (i in 0 until mPoints.size) {
            if (i == 0) {
                val startMarkerOptions = MarkerOptions()
                        .position(mPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.abc_btn_default_mtrl_shape))
                mMap.addMarker(startMarkerOptions)
            } else if (i == mPoints.size - 1) {
                val endMarkerOptions = MarkerOptions()
                        .position(mPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.abc_list_focused_holo))
                mMap.addMarker(endMarkerOptions)
            }
            line.add(mPoints.get(i))
            latLngBuilder.include(mPoints.get(i))
        }
        mMap.addPolyline(line)
        val size = resources.displayMetrics.widthPixels
        val latLngBounds = latLngBuilder.build()
        val track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25)
        mMap.moveCamera(track)
    }
}