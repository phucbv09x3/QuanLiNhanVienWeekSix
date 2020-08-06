package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import kotlinx.android.synthetic.main.activity_add_employee.*

class AddEmployeeActivity : AppCompatActivity() {
    companion object{
        const val DATA_ID="DATA_ID"
        const val DATA_NAME="DATA_NAME"
        const val DATA_AGE="DATA_AGE"
        const val DATA_SALARY="DATA_SALARY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        btn_acceptAdd.setOnClickListener {
            val data = Intent()
            data?.putExtra(DATA_ID, edt_showId.text.toString())
            data?.putExtra(DATA_NAME, edt_showName.text.toString())
            data?.putExtra(DATA_AGE, edt_showAge.text.toString())
            data?.putExtra(DATA_SALARY, edt_showSalary.text.toString())
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}