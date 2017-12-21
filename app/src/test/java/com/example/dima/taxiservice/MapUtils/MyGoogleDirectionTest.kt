package com.example.dima.taxiservice.MapUtils

import com.akexorcist.googledirection.DirectionCallback
import com.google.android.gms.maps.model.LatLng
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Dima on 16.12.2017.
 */
class MyGoogleDirectionTest {
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun getGoogle_map_key() {
        val g=MyGoogleDirection()
        print(g.google_map_key)
    }

    @Test
    fun makeRequest() {
        val g=MyGoogleDirection()
//        g.makeRequest(LatLng(0.0,0.0).toString(), LatLng(0.0,0.0).toString())
    }

    @Mock var call:DirectionCallback?=null
    @Test
    fun makeRequest1() {
        val g=MyGoogleDirection()
        g.makeRequest(LatLng(0.0,0.0), LatLng(0.0,0.0),callback = call!!)
    }

}