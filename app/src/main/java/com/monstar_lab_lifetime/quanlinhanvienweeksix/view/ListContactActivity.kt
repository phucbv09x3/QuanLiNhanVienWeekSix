package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.repository.APIContactRepository
import com.monstar_lab_lifetime.quanlinhanvienweeksix.Interface.OnItemClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter.ContactAdapter
import com.monstar_lab_lifetime.quanlinhanvienweeksix.databinding.ActivityListContactBinding
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.*
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contacts
import com.monstar_lab_lifetime.quanlinhanvienweeksix.viewmodel.ContactViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_employee.*
import kotlinx.android.synthetic.main.activity_list_contact.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ListContactActivity : AppCompatActivity(),
    OnItemClick {
    private var mAdapter = ContactAdapter(this)
    private var mRetrofit: Retrofit? = null
    private var mContact: APIContactRepository? = null
    private var mListContact = mutableListOf<Contact>()

    companion object {
        private const val REQUES_CODE = 0
    }

    private lateinit var binding: ActivityListContactBinding
    private lateinit var model: ContactViewModel
    private var mDispose: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_contact)
        model = ContactViewModel()

        binding.rcyListContact.apply {
            layoutManager = LinearLayoutManager(this@ListContactActivity)
            adapter = ContactAdapter(this@ListContactActivity)
        }
        binding.data = model
        binding.lifecycleOwner = this
        btn_add.setOnClickListener {
            val intentAdd = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intentAdd, REQUES_CODE)
        }
    }
    fun getList(){
        mDispose = model.getContact()
        model.contact.observe(this, object : androidx.lifecycle.Observer<MutableList<Contact>> {
            override fun onChanged(t: MutableList<Contact>?) {
                (binding.rcyListContact.adapter as ContactAdapter).setList(t!!)
                Toast.makeText(this@ListContactActivity,"success",Toast.LENGTH_SHORT).show()
            }
        }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mDispose?.dispose()
    }

    override fun onResume() {
        super.onResume()
        getList()
    }


    override fun onItemClick(contacts: Contact, position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putString("KEY1", contacts.lastName)
        bundle.putString("KEY2", contacts.email)
        bundle.putString("KEY3", contacts.contactId)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onLongClick(contact: Contact, position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Bạn có muốn chỉnh sửa không!")
        dialog.setPositiveButton("Có") { dialog: DialogInterface, which: Int ->
            val intent = Intent(this, UpdateActivity::class.java)
            val bundle = Bundle()
            bundle.putString("KEY1", contact.email)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        dialog.setNegativeButton("Không") { dialog, which ->
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUES_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val dataFirt = data.getStringExtra(AddContactActivity.DATA_FIRST)
                    val dataLast = data.getStringExtra(AddContactActivity.DATA_LAST)
                    val dataMail = data.getStringExtra(AddContactActivity.DATA_MAIL)
                    mDispose = model.postContact(
                        ContactsPost(
                            ContactPost(
                                dataFirt
                                ,
                                dataLast,
                                dataMail.toString(),
                                Custom("xin chao")
                            )
                        )
                    )


                }
            }
        }
    }

//    private fun delete() {
//        val myCallback = object : ItemTouchHelper.SimpleCallback(
//            0,
//            ItemTouchHelper.LEFT
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean = false
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position: Int = viewHolder.adapterPosition
//
//
//                val callDel = mContact?.deletePost(mListContact[position].contactId!!)
//                callDel!!.enqueue(object : Callback<Contacts> {
//                    override fun onFailure(call: Call<Contacts>, t: Throwable) {
//                        Toast.makeText(
//                            applicationContext,
//                            "Error Delete : +${t.message}",
//                            Toast.LENGTH_LONG
//                        )
//                            .show()
//                        getList()
//                    }
//
//                    override fun onResponse(
//                        call: Call<Contacts>,
//                        response: Response<Contacts>
//                    ) {
//                        if (response.isSuccessful) {
//                            this@ListContactActivity.mListContact = response.body()!!.contacts
//
//                            Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                }
//                )
//                mListContact.removeAt(position)
//                mAdapter.notifyDataSetChanged()
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(myCallback)
//        itemTouchHelper.attachToRecyclerView(rcy_listContact)
//        mAdapter.setList(mListContact)
    //}
}