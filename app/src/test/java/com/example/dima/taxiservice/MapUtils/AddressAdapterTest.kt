package com.example.dima.taxiservice.MapUtils

import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Dima on 16.12.2017.
 */
class AddressAdapterTest {
    @Mock var geocoder:Geocoder?=null

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Throws(Exception::class)
    fun findAddress() {
        var aa:AddressAdapter= AddressAdapter(geocoder!!,{location, address, request ->  })
        aa.addressListener
    }

    @Test
    @Throws(Exception::class)
    fun findAddress1() {
        var aa:AddressAdapter= AddressAdapter(geocoder!!,{location, address, request -> print(address)})
//        aa.findAddress(LatLng(0.0,0.0),0)
        aa.geocoder
    }

}