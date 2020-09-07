package com.monstar_lab_lifetime.quanlinhanvienweeksix.repository

import android.database.Observable
import androidx.lifecycle.MutableLiveData
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.*
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contacts
import retrofit2.http.*


interface APIContactRepository {

    //
    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json",
            "autopilotapikey:043886198dab40f294966e5d481c2f78"]
    )
    @GET("contacts/bookmark")
    fun getListContact(): io.reactivex.Observable<Contacts>///retrofit2.Call<Contacts>
    //


    //
    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json",
            "autopilotapikey:043886198dab40f294966e5d481c2f78"]
    )
    @POST("contact")
    fun postContact(
        @Body
        data: ContactsPost
    ): io.reactivex.Observable<ContactsPost>
    //


    //
    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json",
            "autopilotapikey:043886198dab40f294966e5d481c2f78"]
    )
    @DELETE("contact/{contact_id}")
    fun deletePost(@Path("contact_id") id: String): retrofit2.Call<Contacts>
    //

}