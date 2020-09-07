package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter.ContactAdapter
import com.monstar_lab_lifetime.quanlinhanvienweeksix.databinding.ActivityListContactBinding
import com.monstar_lab_lifetime.quanlinhanvienweeksix.repository.APIContactRepository
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.ContactPost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.ContactsPost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Custom
import com.monstar_lab_lifetime.quanlinhanvienweeksix.viewmodel.ContactViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_employee.*
import kotlinx.android.synthetic.main.activity_add_employee.edt_firstNameUpdate
import kotlinx.android.synthetic.main.activity_add_employee.edt_lastNameUpdate
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_update.tv_showNameUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateActivity : AppCompatActivity() {
    private var mRetrofit: Retrofit? = null
    private var mContact: APIContactRepository? = null

    private lateinit var binding: ActivityListContactBinding
    private lateinit var model: ContactViewModel
    private var mDispose: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_update)

        model= ContactViewModel()

//        binding.rcyListContact.apply {
//            layoutManager= LinearLayoutManager(this@UpdateActivity)
//            adapter= ContactAdapter(this@UpdateActivity)
//        }
        binding.data=model
        binding.lifecycleOwner=this

        btn_update.setOnClickListener {
            mDispose=model.postContact(ContactsPost(ContactPost(edt_firstNameUpdate.text.toString()
                ,edt_lastNameUpdate.text.toString(),tv_showNameUpdate.text.toString(),Custom("xin chao"))))

            model.contact.observe(this, object :androidx.lifecycle.Observer<MutableList<Contact>> {
                override fun onChanged(t: MutableList<Contact>?) {
                    ((binding.rcyListContact.adapter as ListContactActivity)as ContactAdapter).setList(t!!)
                }

            })
        }
//        mRetrofit = Retrofit.Builder()
//            .baseUrl("https://private-amnesiac-8522d-autopilot.apiary-proxy.com/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        mContact = mRetrofit?.create(APIContactRepository::class.java)
//
//        val data = intent
//        val bundle = data.extras
//        val email = bundle!!.getString("KEY1", "")
//        tv_showNameUpdate.setText(email)
//
//        btn_update.setOnClickListener {
//            var callPost = mContact!!.postContact(
//                data = ContactsPost(
//                    ContactPost(
//                        edt_firstNameUpdate.text.toString(),
//                        edt_lastNameUpdate.text.toString(),
//                        tv_showNameUpdate.text.toString(),
//                        custom = Custom("helo")
//                    )
//                )
//            )
//
//            Log.d("tg", callPost.toString())
//            callPost?.enqueue(object : Callback<ContactsPost> {
//                override fun onFailure(call: Call<ContactsPost>, t: Throwable) {
//                    Toast.makeText(
//                        applicationContext,
//                        "Error+${t.message}",
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                }
//
//                override fun onResponse(
//                    call: Call<ContactsPost>,
//                    response: Response<ContactsPost>
//                ) {
//                    if (response.isSuccessful) {
//                        Toast.makeText(
//                            applicationContext,
//                            "success",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        finish()
//                    }
//                }
//
//            })
//
//
//        }
    }
}