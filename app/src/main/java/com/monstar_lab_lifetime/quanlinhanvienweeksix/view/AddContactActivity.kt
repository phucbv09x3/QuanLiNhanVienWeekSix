package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import kotlinx.android.synthetic.main.activity_add_employee.*

class AddContactActivity : AppCompatActivity() {
    private var mEmail =
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    companion object {
        var DATA_FIRST = "DATA_FIRST"
        var DATA_LAST = "DATA_LAST"
        var DATA_MAIL = "DATA_MAIL"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        edt_mail.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_mail.text.toString().trim().matches(mEmail.toRegex())){
                    iv_goneandshow.visibility=View.VISIBLE
                }else{
                    iv_goneandshow.visibility=View.GONE
                }
            }

        })
        btn_acceptAdd.setOnClickListener {
            if (edt_mail.text.toString().trim().matches(mEmail.toRegex())){
                val data = Intent()
                data?.putExtra(DATA_FIRST, edt_firstName.text.toString())
                data?.putExtra(DATA_LAST, edt_lastName.text.toString())
                data?.putExtra(DATA_MAIL, edt_mail.text.toString())
                setResult(Activity.RESULT_OK, data)
                finish()
            }else{
                Toast.makeText(this,"Vui long nhập đúng email",Toast.LENGTH_SHORT).show()
            }


        }

    }

}




