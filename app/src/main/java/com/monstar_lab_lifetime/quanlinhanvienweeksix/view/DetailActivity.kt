package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val intent=intent
        val bundle=intent.extras
       val id=bundle?.getInt("KEY1",0)
        val name=bundle?.getString("KEY2","")
        val age=bundle?.getInt("KEY3",0)
        val salary=bundle?.getLong("KEY4",0)
        tv_showId.text = id.toString()
        tv_showAge.text = age.toString()
        tv_showName.text = name
        tv_showSalary.text = salary.toString()
    }
}