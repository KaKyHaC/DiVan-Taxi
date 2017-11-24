package com.example.dima.taxiservice

/**
 * Created by Dima on 24.11.2017.
 */
enum class Role{
    User,Driver,None
}
interface LoadingView{
    fun showLoading()
    fun hideLoading()
}