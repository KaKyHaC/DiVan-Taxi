package com.example.dima.taxiservice

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.widget.EditText
import android.widget.Toast
import com.example.dima.taxiservice.MapUtils.AddressAdapter
import com.example.dima.taxiservice.MapUtils.LocationFinder
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MainActivity : AppCompatActivity() , OnMapReadyCallback, LocationFinder.StatusListener, AddressAdapter.AddressListener, LocationFinder.Ifinder {



    private lateinit var mMap: GoogleMap
    internal var locationFinder: LocationFinder?=null
    internal var addressAdapter: AddressAdapter?=null
    lateinit var etCur:EditText
    lateinit var etTar:EditText

    private fun init(){
        etCur=findViewById(R.id.etCurrLoc)
        etTar=findViewById(R.id.etTarget)
        try {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val geocoder = Geocoder(this, Locale.getDefault())
            addressAdapter = AddressAdapter(this, geocoder, this)
            locationFinder = LocationFinder(this, locationManager, addressAdapter)
        }catch (e:Exception){
            print(e.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapMy) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener { latLng ->mapClickListener(latLng) }
        mMap.isMyLocationEnabled=true
        locationFinder?.startFinding(this)
    }

    private fun mapClickListener(latLng: LatLng){
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(latLng))
        val geocoder = Geocoder(this, Locale.getDefault())
        addressAdapter = AddressAdapter(this, geocoder, AddressAdapter.AddressListener {location, address, result ->
            address?.let {
                etTar.text=SpannableStringBuilder(formatAddress(address))
            }
        })

        addressAdapter?.findAddress(latLng)
    }

    override fun onSatusChange(s: String?) {
//        Toast.makeText(this,s, Toast.LENGTH_SHORT).show()
    }

    val zoom=15f
    override fun onAddressChange(location: LatLng?, address: Address?, result: AddressAdapter.AddressListener.Result?) {
        if (result == AddressAdapter.AddressListener.Result.Success&&location!=null) {
            val loc = location
            mMap.addMarker(MarkerOptions().position(loc).title(address?.thoroughfare))
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(loc,zoom)))
            locationFinder?.stopFinding()
            address?.let {
                etCur.text = SpannableStringBuilder(formatAddress(address))
            }
        }
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
            locationFinder?.startFinding(this)
        }
    }

    private fun formatAddress(address: Address): String {
        val sb = StringBuilder()
//        sb.append(address.countryName)
//        sb.append(",")
//        sb.append(address.locality)
//        sb.append(",")
        sb.append(address.thoroughfare)
        sb.append(",")
        sb.append(address.featureName)
        sb.append('.')
        return sb.toString()
    }
}

