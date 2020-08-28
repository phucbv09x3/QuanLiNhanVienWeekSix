package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var mText:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val intent = intent
        val bundle = intent.extras
        val name = bundle?.getString("KEY1", "")
        val mail = bundle?.getString("KEY2", "")
        val contactId = bundle?.getString("KEY3", "")
        this.mText=contactId
        tv_showNameUpdate.setText(name)
        tv_showMail.text=mail
        tv_showId.text=contactId
    }
    fun clikShow(view: View) {
        tv_showId.visibility = View.GONE
        tv_showIdGone.visibility=View.VISIBLE
        tv_showIdGone.setText(mText)
    }
    fun clikShowTextMax(view: View) {
        tv_showId.visibility = View.VISIBLE
        tv_showIdGone.visibility=View.GONE
        tv_showId.text=mText
    }
}