package com.example.dima.taxiservice.MapUtils

import retrofit.http.GET
import retrofit.http.Query
import retrofit.*
import com.example.dima.taxiservice.MapUtils.MyGoogleDirection.RouteApi
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import retrofit.RestAdapter
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.AvoidType
import com.akexorcist.googledirection.GoogleDirection.withServerKey
import com.akexorcist.googledirection.model.Direction
import com.example.dima.taxiservice.R


/**
 * Created by Dima on 24.11.2017.
 */
class MyGoogleDirection {
    class RouteResponse {
        private var routes: List<Route>? = null

        val points: String?
            get() = this.routes!![0].overview_polyline!!.points

        internal inner class Route {
            var overview_polyline: OverviewPolyline? = null
        }

        internal inner class OverviewPolyline {
            var points: String? = null
        }
    }
    interface RouteApi {
        @GET("/maps/api/directions/json")
        fun getRoute(
                @Query(value = "origin", encodeValue = false) position: String,
                @Query(value = "destination", encodeValue = false) destination: String,
                @Query("sensor") sensor: Boolean,
                @Query("language") language: String): RouteResponse
    }

    val google_map_key="AIzaSyAlBf3d83nf8Sl-o5as5aWqxFIDY3CzfqY"
    fun makeRequest(p1:LatLng,p2:LatLng,callback: DirectionCallback){
        GoogleDirection.withServerKey(google_map_key)
                .from(LatLng(37.7681994, -122.444538))
                .to(LatLng(37.7749003, -122.4034934))
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(callback)
    }
    fun makeRequest(position: String,destination: String):List<LatLng>{
        val restAdapter = RestAdapter.Builder()
                .setEndpoint("https://maps.googleapis.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val routeService = restAdapter.create(RouteApi::class.java)
        val routeResponse = routeService.getRoute(position, destination, true, "ru")

        return PolyUtil.decode(routeResponse.points)
    }

}