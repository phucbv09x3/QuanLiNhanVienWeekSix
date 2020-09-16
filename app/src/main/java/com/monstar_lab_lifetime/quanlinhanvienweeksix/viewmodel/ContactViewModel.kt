package com.monstar_lab_lifetime.quanlinhanvienweeksix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.ContactPost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contacts
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.ContactsPost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.repository.APIContactRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactViewModel : ViewModel() {
    var isBoolean: MutableLiveData<Boolean> = MutableLiveData(true)
    var mAPIRepository: APIContactRepository
    var contact = MutableLiveData<MutableList<Contact>>()
    var contactPost = MutableLiveData<ContactPost>()
    var resultAPI: MutableLiveData<String> = MutableLiveData("start")
    lateinit var callPost: Call<ContactsPost>
    lateinit var callDel: Call<Contacts>

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-type", "application/json")
                    .addHeader("autopilotapikey", "043886198dab40f294966e5d481c2f78")
                    .build()
            chain.proceed(request)
        }
        mAPIRepository = Retrofit.Builder()
            .baseUrl("https://private-amnesiac-8522d-autopilot.apiary-proxy.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build().create(APIContactRepository::class.java)
    }

    fun getContactVM() {
        isBoolean.value = true
        val call = mAPIRepository.getListContact()
        call.enqueue(object : Callback<Contacts> {
            override fun onFailure(call: Call<Contacts>, t: Throwable) {
                isBoolean.value = false
                resultAPI.value="errorGetList"
            }
            override fun onResponse(call: Call<Contacts>, response: retrofit2.Response<Contacts>) {
                contact.value = response.body()?.contacts
                isBoolean.value = false
            }

        })

    }


    fun postContactVM(dataPost: ContactsPost) {
        isBoolean.value = true
        var call = mAPIRepository.postContact(dataPost)
        this.callPost = call
        call.enqueue(object : Callback<ContactsPost> {
            override fun onFailure(call: Call<ContactsPost>, t: Throwable) {
                isBoolean.value = false
                resultAPI.value = "errorPost"
            }

            override fun onResponse(
                call: Call<ContactsPost>,
                response: retrofit2.Response<ContactsPost>
            ) {
                if (response.isSuccessful) {
                    isBoolean.value = false
                    contactPost.value = response.body()?.contact
                    getContactVM()
                }

            }

        })


    }

    fun deleteContactVM(contactID: String) {
        isBoolean.value = true
        val call = mAPIRepository.deletePost(contactID)
        this.callDel = call
        call.enqueue(object : Callback<Contacts> {
            override fun onFailure(call: Call<Contacts>, t: Throwable) {
                isBoolean.value = false
                resultAPI.value="errorDelete"
            }

            override fun onResponse(call: Call<Contacts>, response: retrofit2.Response<Contacts>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        isBoolean.value = false
                        contact.value = response.body()!!.contacts
                        getContactVM()
                    }
                }


            }

        })

    }


//    private val builder = OkHttpClient.Builder()
//        .readTimeout(5000, TimeUnit.MILLISECONDS)
//        .writeTimeout(5000, TimeUnit.MILLISECONDS)
//        .connectTimeout(5000, TimeUnit.MILLISECONDS)
//        .retryOnConnectionFailure(true)
//        .addInterceptor(intercept())
//        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//        .build()
//    }
//    fun getContact(): Disposable {
//        isLoading.set(true)
//        return mAPIRepository.getListContact()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                isLoading.set(false)
//                contact.value = it.contacts
//            }, {
//                //that bai
//                Log.d("e", it.message.toString())
//                isLoading.set(false)
//            })
//
//    }

//    fun postContact(dataC: ContactsPost): Disposable {
//        isLoading.set(true)
//        return mAPIRepository.postContact(dataC)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                isLoading.set(false)
//                contactPost.value=it.contact
//                getContact()
//            }, {
//                isLoading.set(false)
//            })
//    }
//
//    fun deleteContact(contactId: String): Disposable {
//        isLoading.set(true)
//        return mAPIRepository.deletePost(contactId)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                //thanh cong
//                isLoading.set(false)
//                contact.value=it.contacts
//                getContact()
//            }, {
//                //that bai
//                Log.d("e", it.message.toString())
//                isLoading.set(false)
//            })
//
//    }

//    override fun intercept(chain: Interceptor.Chain): Response=chain.run {
//        proceed(
//            request()
//                .newBuilder()
//                .addHeader("Content-type", "application/json")
//                .addHeader("Accept", "application/json")
//                .removeHeader("User-Agent")
//                .addHeader("autopilotapikey", "043886198dab40f294966e5d481c2f78")
//                .build()
//        )
//    }

}