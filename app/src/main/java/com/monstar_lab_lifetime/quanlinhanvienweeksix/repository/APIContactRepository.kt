package com.monstar_lab_lifetime.quanlinhanvienweeksix.repository

import android.app.Application
import android.database.Observable
import androidx.lifecycle.MutableLiveData
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.*
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contacts
import retrofit2.http.*


interface APIContactRepository {

    @GET("contacts/bookmark")
    fun getListContact(): retrofit2.Call<Contacts>

    @POST("contact")
    fun postContact(
        @Body
        data: ContactsPost
    ):retrofit2.Call<ContactsPost>

    @DELETE("contact/{contact_id}")
    fun deletePost(@Path("contact_id") id: String): retrofit2.Call<Contacts>

}