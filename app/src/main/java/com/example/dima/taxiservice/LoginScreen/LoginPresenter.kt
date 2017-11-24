package com.example.dima.taxiservice.LoginScreen

import com.example.dima.taxiservice.LoadingView

/**
 * Created by Dima on 24.11.2017.
 */
interface ILoginView:LoadingView{
    fun onSuccessLogin()
    fun onPasswordError()
    fun onLoginError()
}
interface ILoginModel{

}
class LoginPresenter (val view: ILoginView,val model: ILoginModel){
    fun AutoLogin(){

    }
    fun tryLogin(log:String,password:String){
        
    }
}