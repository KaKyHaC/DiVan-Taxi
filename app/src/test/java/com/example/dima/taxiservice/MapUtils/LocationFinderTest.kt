package com.example.dima.taxiservice.MapUtils

import android.content.Context
import android.location.Location
import android.location.LocationManager
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

import org.junit.Assert.*
import org.mockito.Mock

/**
 * Created by Dima on 16.12.2017.
 */
class LocationFinderTest {
    lateinit var lf:LocationFinder
    @Mock var ll:LocationFinder.LocationFinderListener?=null
    @Mock var lm:LocationManager?=null
    @Mock var ifinder: LocationFinder.Ifinder?=null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lf = LocationFinder(ll!!,lm!!,0)
    }

    @Test
    @Throws(Exception::class)
    fun startFinding() {
//        lf.startFinding(ifinder)
    }

    @Test
    @Throws(Exception::class)
    fun stopFinding() {
        lf.stopFinding()
    }

    @Test
    @Throws(Exception::class)
    fun onLocationChanged() {
        lf.onLocationChanged(Location(""))
    }

    @Test
    @Throws(Exception::class)
    fun onStatusChanged() {
        lf.onStatusChanged("",0,null)
    }

    @Test
    @Throws(Exception::class)
    fun onProviderEnabled() {
        lf.onProviderEnabled("")
    }

    @Test
    @Throws(Exception::class)
    fun onProviderDisabled() {
        lf.onProviderDisabled("")
    }

}