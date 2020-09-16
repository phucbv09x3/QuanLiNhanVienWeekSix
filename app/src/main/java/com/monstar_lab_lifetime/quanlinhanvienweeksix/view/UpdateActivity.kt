package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.repository.APIContactRepository
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.ContactPost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.ContactsPost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Custom
import com.monstar_lab_lifetime.quanlinhanvienweeksix.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_add_employee.*
import kotlinx.android.synthetic.main.activity_update.*
import retrofit2.Retrofit

class UpdateActivity : AppCompatActivity() {
    private var mRetrofit: Retrofit? = null
    private var mContact: APIContactRepository? = null
    companion object {
        var DATA_FIRST = "DATA_FIRST"
        var DATA_LAST = "DATA_LAST"
        var DATA_MAIL = "DATA_MAIL"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val data = intent
        val bundle = data.extras
        val email = bundle!!.getString("KEY1", "")
        tv_showNameUpdate.text=email
        // binding.lifecycleOwner = this

        btn_update.setOnClickListener {
            val data = Intent()
            data?.putExtra(UpdateActivity.DATA_FIRST, edt_firstNameUpdate.text.toString())
            data?.putExtra(UpdateActivity.DATA_LAST, edt_lastNameUpdate.text.toString())
            data?.putExtra(UpdateActivity.DATA_MAIL, tv_showNameUpdate.text.toString())
            setResult(Activity.RESULT_OK, data)
            finish()
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