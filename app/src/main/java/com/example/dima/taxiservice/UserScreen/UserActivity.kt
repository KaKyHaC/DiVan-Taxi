package com.example.dima.taxiservice.UserScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.SpannableStringBuilder
import android.widget.EditText
import com.example.dima.taxiservice.MapUtils.AddressAdapter
import com.example.dima.taxiservice.MapUtils.LocationFinder
import com.example.dima.taxiservice.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class UserActivity : AppCompatActivity() , OnMapReadyCallback,IUserView {
    private lateinit var mMap: GoogleMap
    private lateinit var etCur:EditText
    private lateinit var etTar:EditText
    private lateinit var bOrder:FloatingActionButton

    private lateinit var presenter:UserPresenter

    private fun init(){
        etCur=findViewById(R.id.etCurrLoc)
        etTar=findViewById(R.id.etTarget)
        bOrder=findViewById(R.id.bCreateOrder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapMy) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
        presenter= UserPresenter(this,UserModel())

        bOrder.setOnClickListener {
            presenter.createOrder()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener { latLng ->presenter.onMapClick(latLng) }
        mMap.isMyLocationEnabled=true
        presenter.FindMyLocation()
    }

    override fun getContext(): Context {
        return this
    }

    val requestCode=1
    override fun requestPermissions(vararg permission: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(permission,requestCode)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode==this.requestCode){
            presenter.FindMyLocation()
        }
    }

    override fun showLoading() {
        etCur.isEnabled=false
        etTar.isEnabled=false
    }
    override fun hideLoading() {
        etCur.isEnabled=true
        etTar.isEnabled=true
    }
    override fun moveToLocation(location: LatLng, zoom: Float) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,zoom))
    }
    override fun addMarker(location: LatLng, name: String?) {
        mMap.addMarker(MarkerOptions().position(location).title(name))
    }
    override fun setCurrentPlaceName(name: String) {
        etCur.text=SpannableStringBuilder(name)
    }
    override fun setTargetPlaceName(name: String) {
        etTar.text=SpannableStringBuilder(name)
    }
    override fun clearMap() {
        mMap.clear()
    }
}

