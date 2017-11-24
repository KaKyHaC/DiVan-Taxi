package com.example.dima.taxiservice.Screens

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Dima on 24.11.2017.
 */
enum class Role{
    User,Driver,None
}
interface LoadingView{
    fun showLoading()
    fun hideLoading()
}
interface MapView{
    fun moveToLocation(location: LatLng, zoom:Float)
    fun addMarker(location: LatLng, name: String?)
    fun clearMap()
    fun buildPolyline(mPoints:List<LatLng>)
}