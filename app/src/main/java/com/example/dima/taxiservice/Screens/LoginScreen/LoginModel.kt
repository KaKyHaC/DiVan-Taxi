package com.example.dima.taxiservice.Screens.LoginScreen

import com.example.dima.taxiservice.Screens.Role

/**
 * Created by Dima on 24.11.2017.
 */
class LoginModel:ILoginModel {
    var db= Pair<String,String>("dimas@kavkaz","vanal")
    override fun tryLogin(name: String, password: String): Role? {
        if(name.equals(db.first)&&password.equals(db.second))
            return Role.User
        return null
    }

    override fun getAutoCompletDate(): Pair<String, String> {
        return db
    }

    override fun saveDataToAutoComplet(name: String, password: String) {
        db=Pair(name,password)
    }
}