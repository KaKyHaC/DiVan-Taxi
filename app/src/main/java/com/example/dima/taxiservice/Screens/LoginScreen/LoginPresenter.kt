package com.example.dima.taxiservice.Screens.LoginScreen

import com.example.dima.taxiservice.Screens.LoadingView
import com.example.dima.taxiservice.Screens.Role

/**
 * Created by Dima on 24.11.2017.
 */
interface ILoginView: LoadingView {
    fun onSuccessLogin(role: Role)
    fun onPasswordError(message:String)
    fun onLoginError(message:String)
    fun autoCompletFilds(name:String,password: String)
}
interface ILoginModel{
    fun saveDataToAutoComplet(name: String,password: String)
    fun getAutoCompletDate():Pair<String,String>?
    fun tryLogin(name: String,password: String): Role?
}
class LoginPresenter (val view: ILoginView,val model: ILoginModel){
    fun AutoLogin(){
        val p=model.getAutoCompletDate()?:return
        view.autoCompletFilds(p.first,p.second)
        tryLogin(p.first,p.second)
//        view.showLoading()
//        Thread.sleep(2000)
//        view.hideLoading()
//        view.onSuccessLogin(Role.User)
    }
    fun tryLogin(log:String,password:String){
        view.showLoading()
        var isValid=true
        if(!isEmailValid(log)) {
            view.onLoginError("login must contain '@'")
            view.hideLoading()
            isValid=false
        }
        if(!isPasswordValid(password)) {
            view.onPasswordError("password length must be 4 or bigger symbol")
            view.hideLoading()
            isValid=false
        }
        if(!isValid)
            return

        val res=model.tryLogin(log,password)
        res?.let {
            model.saveDataToAutoComplet(log,password)
            view.onSuccessLogin(res)
        }
        view.hideLoading()
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }
}