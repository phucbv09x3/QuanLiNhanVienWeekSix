package com.monstar_lab_lifetime.quanlinhanvienweeksix.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        val mRetrofit = Retrofit.Builder()
        .baseUrl("https://private-amnesiac-8522d-autopilot.apiary-proxy.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
}