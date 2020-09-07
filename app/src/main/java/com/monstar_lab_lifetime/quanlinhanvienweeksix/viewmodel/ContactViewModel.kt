package com.monstar_lab_lifetime.quanlinhanvienweeksix.viewmodel

import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.ContactPost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contacts
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.ContactsPost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.repository.APIContactRepository
import com.monstar_lab_lifetime.quanlinhanvienweeksix.view.ListContactActivity
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.newThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactViewModel {
    val isLoading=ObservableBoolean(false)
     private var  repository : APIContactRepository
    var contact=MutableLiveData<MutableList<Contact>>()
    var contactPost=MutableLiveData<ContactPost>()
    init {
       repository=Retrofit.Builder()
            .baseUrl("https://private-amnesiac-8522d-autopilot.apiary-proxy.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(APIContactRepository::class.java)
    }
    fun getContact():Disposable{
        isLoading.set(true)
        return repository.getListContact()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //thanh cong
                isLoading.set(false)
                contact.value = it.contacts
            }, {
                //that bai
                isLoading.set(false)
            })
    }
    fun postContact(dataC: ContactsPost):Disposable{
        isLoading.set(true)
        return repository.postContact(dataC)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.set(false)
                contactPost.value=it.contact
                getContact()
            },{
                isLoading.set(false)
            })
    }

}