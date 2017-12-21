package com.example.dima.taxiservice.Screens.UserScreen

import android.location.Address
import com.example.dima.taxiservice.MapUtils.AddressAdapter
import com.example.dima.taxiservice.MapUtils.LocationFinder
import com.google.android.gms.maps.model.LatLng
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Created by Dima on 19.12.2017.
 */
class UserPresenterTest {
    @Mock lateinit var iv:IUserView
    @Mock lateinit var im:IUserModel
    @Mock lateinit var lf:LocationFinder
    @Mock lateinit var aa:AddressAdapter
    lateinit var up:UserPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        up=UserPresenter(iv,im)
    }

    @Test
    fun `getLocationFinder$production_sources_for_module_app`() {
        assertNull(up.locationFinder)
    }

    @Test
    fun `setLocationFinder$production_sources_for_module_app`() {
        up.locationFinder= lf
        assertEquals(up.locationFinder,lf)
    }

    @Test
    fun `getAddressAdapter$production_sources_for_module_app`() {
        assertNull(up.addressAdapter)
    }

    @Test
    fun `setAddressAdapter$production_sources_for_module_app`() {
        up.addressAdapter=aa
        assertEquals(up.addressAdapter,aa)
    }

    @Test
    fun getCurMarker() {
        assertNull(up.curMarker)
    }

    @Test
    fun setCurMarker() {
        val m= Pair(LatLng(0.0,0.0), Address(Locale("")))
        up.curMarker=m
        assertEquals(m,up.curMarker)
    }

    @Test
    fun getTarMarker() {
        assertNull(up.tarMarker)
    }

    @Test
    fun setTarMarker() {
        val m= Pair(LatLng(0.0,0.0), Address(Locale("")))
        up.tarMarker=m
        assertEquals(m,up.tarMarker)
    }

    @Test
    fun onMapClick() {
        up.onMapClick(LatLng(0.0,0.0))
    }

    @Test
    fun findMyLocation() {
        up.FindMyLocation()
    }

    @Test
    fun createOrder() {
        up.createOrder()
    }

    @Test
    fun onAddressFinded() {
        up.onAddressFinded(null,null,0)
    }

    @Test
    fun onSatusChange() {
        up.onSatusChange("")
    }

    @Test
    fun onLocationFinded() {
        up.onLocationFinded(null)
    }

    @Test
    fun getView() {
        assertEquals(up.view,iv)
    }

    @Test
    fun getModel() {
        assertEquals(up.model,im)
    }

}