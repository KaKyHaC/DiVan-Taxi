package com.example.dima.taxiservice.Screens.LoginScreen

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Dima on 19.12.2017.
 */
class LoginPresenterTest {
    @Mock var iv:ILoginView?=null
    @Mock var im:ILoginModel?=null
    lateinit var lp:LoginPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lp= LoginPresenter(iv!!,im!!)
    }

    @Test
    fun autoLogin() {
        lp.AutoLogin()
    }

    @Test
    fun tryLogin() {
        lp.tryLogin("","")
    }

    @Test
    fun getView() {
        assertEquals(lp.view,iv)
    }

    @Test
    fun getModel() {
        assertEquals(lp.model,im)
    }

}