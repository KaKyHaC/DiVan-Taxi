package com.example.dima.taxiservice.Screens.LoginScreen

import com.example.dima.taxiservice.Screens.Role
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.MockitoAnnotations

/**
 * Created by Dima on 19.12.2017.
 */
class LoginModelTest {
    var lm:LoginModel= LoginModel()
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getDb() {
        val p=lm.db
        val f=p.first
        val s=p.second
        assertNotEquals(f,s)
        assertEquals(f,f)
        assertEquals(s,s)
    }

    @Test
    fun setDb() {
        val p=Pair<String,String>("asb","fgh")
        lm.db=p
        assertEquals(p.second,lm.db.second)
        assertEquals(p.first,lm.db.first)
    }

    @Test
    fun tryLogin() {
        val role=lm.tryLogin("","")
        assertEquals(role, null)

        val role1=lm.tryLogin(lm.db.first,lm.db.second)
        assertNotEquals(role1,Role.None)
    }

    @Test
    fun getAutoCompletDate() {
        val d=lm.getAutoCompletDate()
        assertEquals(d.first,lm.db.first)
        assertEquals(d.second,lm.db.second)
    }

    @Test
    fun saveDataToAutoComplet() {
        val p=Pair<String,String>("asb","fgh")
        lm.saveDataToAutoComplet(p.first,p.second)
        assertEquals(p,lm.getAutoCompletDate())
    }

}