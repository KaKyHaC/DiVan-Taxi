package com.example.dima.taxiservice.UserScreen

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.text.SpannableStringBuilder
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.model.Direction
import com.example.dima.taxiservice.LoadingView
import com.example.dima.taxiservice.MapUtils.AddressAdapter
import com.example.dima.taxiservice.MapUtils.LocationFinder
import com.example.dima.taxiservice.MapUtils.MyGoogleDirection
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.PolyUtil
import java.util.*

/**
 * Created by Dima on 24.11.2017.
 */

interface IUserView:LoadingView, LocationFinder.Ifinder{
    fun moveToLocation(location:LatLng,zoom:Float)
    fun addMarker(location: LatLng,name: String?)
    fun clearMap()
    fun setCurrentPlaceName(name:String)
    fun setTargetPlaceName(name:String)
    fun buildPolyline(mPoints:List<LatLng>)
}
interface IUserModel{

}
class UserPresenter(val view:IUserView,val model: IUserModel) :LocationFinder.LocationFinderListener
        , AddressAdapter.AddressListener{
    companion object {
        val curAddressRequest=123
        val tarAddressRequest=2
    }

    var curMarker:Pair<LatLng,Address>?=null
    var tarMarker:Pair<LatLng,Address>?=null
    internal var locationFinder: LocationFinder?=null
    internal var addressAdapter: AddressAdapter?=null
    init {
        try {
            val locationManager = view.getContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val geocoder = Geocoder(view.getContext(), Locale.getDefault())
            addressAdapter = AddressAdapter( geocoder, this)
            locationFinder = LocationFinder(this, locationManager, curAddressRequest)
        }catch (e:Exception){
            print(e.toString())
        }
    }

    fun onMapClick(pos:LatLng){
        addressAdapter?.findAddress(pos, tarAddressRequest
                ,{location, address, request ->
            if(location!=null&&address!=null){
                view.clearMap()
                view.addMarker(curMarker!!.first, curMarker!!.second.featureName)
                view.addMarker(location,address.featureName)
                view.setTargetPlaceName(formatAddress(address))
                tarMarker= Pair(location,address)
            }
        })
    }
    fun FindMyLocation(){
        locationFinder?.startFinding(view)
    }
    fun createOrder(){
        if(curMarker!=null&&tarMarker!=null) {
//            val list = MyGoogleDirection().makeRequest(curMarker?.first.toString(), tarMarker?.first.toString())
//            view.buildPolyline(list)

//            MyGoogleDirection().makeRequest(curMarker!!.first,tarMarker!!.first,object:DirectionCallback{
//                override fun onDirectionSuccess(direction: Direction?, rawBody: String?) {
//                    view.buildPolyline(PolyUtil.decode(rawBody))
//                }
//
//                override fun onDirectionFailure(t: Throwable?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//            })
        }
    }

    private fun buildDirection(p1:LatLng,p2:LatLng){

    }
    val zoom=15f
    override fun onAddressFinded(location: LatLng?, address: Address?, request: Int) {
        if (location!=null&&address!=null&&request== curAddressRequest) {
            curMarker=Pair(location,address)
            view.moveToLocation(location,zoom)
            view.setCurrentPlaceName(formatAddress(address))
            view.addMarker(location,address.featureName)
            locationFinder?.stopFinding()
        }
    }

    override fun onSatusChange(s: String?) {

    }

    override fun onLocationFinded(location: Location?) {
        if(location!=null)
            addressAdapter?.findAddress(LatLng(location.latitude,location.longitude), curAddressRequest)
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