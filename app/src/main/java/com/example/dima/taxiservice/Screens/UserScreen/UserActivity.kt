package com.example.dima.taxiservice.Screens.UserScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.SpannableStringBuilder
import android.widget.EditText
import com.example.dima.taxiservice.R
import com.example.dima.taxiservice.Screens.OrderScreen.OrderView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.PolylineOptions



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

    override fun buildPolyline(mPoints:List<LatLng>){
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

    override fun onOrderCreated(from: Pair<LatLng, Address>, to: Pair<LatLng, Address>) {
        startActivity(Intent(this,OrderView().javaClass))
    }
}

