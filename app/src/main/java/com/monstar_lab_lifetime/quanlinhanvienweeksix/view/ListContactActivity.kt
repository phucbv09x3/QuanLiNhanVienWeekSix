package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.APIContactRepository
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.OnItemClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter.ContactAdapter
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.*
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contacts
import kotlinx.android.synthetic.main.activity_list_contact.*
import kotlinx.android.synthetic.main.item_contact.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.zip.Inflater

class ListContactActivity : AppCompatActivity(), OnItemClick {
    private var mAdapter = ContactAdapter(this)
    private var mRetrofit: Retrofit? = null
    private var mContact: APIContactRepository? = null
    private var mListContact = mutableListOf<Contact>()
    private var mListPut = arrayListOf<Contact>()

    companion object {
        private const val REQUES_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_contact)
        rcy_listContact.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://private-amnesiac-8522d-autopilot.apiary-proxy.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mContact = mRetrofit?.create(APIContactRepository::class.java)

        rcy_listContact.adapter = mAdapter
        //getList()
        btn_add.setOnClickListener {
            val intentAdd = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intentAdd, REQUES_CODE)
        }
        delete()
    }

    override fun onResume() {
        super.onResume()
        getList()
        Log.d("a","onResume")

    }

    override fun onStart() {
        super.onStart()
        Log.d("a","start")

    }

    fun getList() {
        val pr = ProgressDialog(this)
        pr.show()

        var call = mContact?.getListContact()

        call?.enqueue(object : Callback<Contacts> {
            override fun onFailure(call: Call<Contacts>, t: Throwable) {
                Toast.makeText(applicationContext, "Error+${t.message}", Toast.LENGTH_LONG).show()
                mAdapter.setList(ArrayList<Contact>())
            }

            override fun onResponse(
                call: Call<Contacts>,
                response: Response<Contacts>
            ) {
                if (response?.isSuccessful) {
                    pr.dismiss()
                    val list = response.body()?.contacts
                    this@ListContactActivity.mListPut = list!! as ArrayList<Contact>
                    this@ListContactActivity.mListContact = list!!
                    list?.let {
                        mAdapter.setList(it)
                    }
                }
            }
        })
    }

    override fun OnItemClick(contacts: Contact, position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putString("KEY1", contacts.name)
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
                    var callPost = mContact!!.postContact(
                        data = ContactsPost(
                            ContactPost(
                                dataFirt,
                                dataLast,
                                dataMail,
                                custom = Custom("helo")
                            )
                        )
                    )

                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Bạn có muốn dừng lại không!")
                    dialog.setNegativeButton("Dừng") { dialog, which ->
                        callPost?.cancel()
                        dialog.dismiss()
                    }
                    dialog.show()
                    Log.d("tg", callPost.toString())
                    callPost?.enqueue(object : Callback<ContactsPost> {

                        override fun onFailure(call: Call<ContactsPost>, t: Throwable) {
                            Toast.makeText(
                                applicationContext,
                                "Error+${t.message}",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                        override fun onResponse(
                            call: Call<ContactsPost>,
                            response: Response<ContactsPost>
                        ) {
                            if (response.isSuccessful) {
                                getList()
                            }
                        }

                    })
                }

            }
        }
    }

    private fun delete() {
        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition


                val callDel = mContact?.deletePost(mListContact[position].contactId!!)
                callDel!!.enqueue(object : Callback<Contacts> {
                    override fun onFailure(call: Call<Contacts>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error Delete : +${t.message}", Toast.LENGTH_LONG)
                            .show()
                        getList()
                    }
                    override fun onResponse(
                        call: Call<Contacts>,
                        response: Response<Contacts>
                    ) {
                        if (response.isSuccessful) {
                            this@ListContactActivity.mListContact = response.body()!!.contacts

                            Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                )
                mListContact.removeAt(position)
                mAdapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(myCallback)
        itemTouchHelper.attachToRecyclerView(rcy_listContact)
        mAdapter.setList(mListContact)
    }
}