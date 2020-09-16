package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.repository.APIContactRepository
import com.monstar_lab_lifetime.quanlinhanvienweeksix.Interface.OnItemClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter.ContactAdapter
import com.monstar_lab_lifetime.quanlinhanvienweeksix.databinding.ActivityListContactBinding
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.*
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact
import com.monstar_lab_lifetime.quanlinhanvienweeksix.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_list_contact.*
import retrofit2.*

class ListContactActivity : AppCompatActivity(),
    OnItemClick {
    // var isLoading = ObservableBoolean(false)
    private var mAdapter = ContactAdapter(this)
    private var mRetrofit: Retrofit? = null
    private var mContact: APIContactRepository? = null
    private var mListContact = mutableListOf<Contact>()

    var mListGet = mutableListOf<Contact>()

    companion object {
        private const val REQUEST_CODE = 0
    }

    private var mTextError:String=""
     var data = MutableLiveData<MutableList<Contact>>()
    lateinit var binding: ActivityListContactBinding
    lateinit var contactViewModel: ContactViewModel

    //var mDispose: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_contact)
        binding.rcyListContact.apply {
            layoutManager = LinearLayoutManager(this@ListContactActivity)
            adapter = ContactAdapter(this@ListContactActivity)
        }
        contactViewModel = ContactViewModel()
        binding.data = contactViewModel
        binding.lifecycleOwner = this
        btn_add.setOnClickListener {
            val intentAdd = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intentAdd, REQUEST_CODE)
        }
        getList()
    }

    private fun getList() {

        var alertDialog = AlertDialog.Builder(this).create()
        // alertDialog.setTitle("***")
        alertDialog.setMessage("Đang tải dữ liệu...")

//        alertDialog.setButton(-1,"Ẩn thông báo này") {dialog: DialogInterface?, which: Int ->
//           //-1 là định danh của  DialogInterface.BUTTON_POSITIVE
//        }
        alertDialog.show()
        var calll = contactViewModel.getContactVM()
        contactViewModel.contact.observe(this, Observer<MutableList<Contact>> {
            it?.let {
                (binding.rcyListContact.adapter as ContactAdapter).setList(it)
            }
        })
        contactViewModel.isBoolean.observe(this, Observer<Boolean> {
            if (it == false) {
                alertDialog.dismiss()
            }
        })


    }

//    private fun getList() {
//        mDispose = mModel.getContact()
//        mModel.contact.observe(this,
//            Observer<MutableList<Contact>> {
//                it?.let {
//                    (binding.rcyListContact.adapter as ContactAdapter).setList(it)
//                    this.mListGet = it
//                }
//            }
//        )
//    }

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
        dialog.setTitle("Bạn có muốn chỉnh sửa hay xóa!")
        dialog.setPositiveButton("Chỉnh sửa") { dialog: DialogInterface, which: Int ->
            val intent = Intent(this, UpdateActivity::class.java)
            val bundle = Bundle()
            bundle.putString("KEY1", contact.email)
            intent.putExtras(bundle)
            startActivityForResult(intent, 9)
        }
        dialog.setNegativeButton("Xóa") { dialog, which ->

            var alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Load....")
            contactViewModel.deleteContactVM(contact.contactId)
            contactViewModel.isBoolean.observe(this, Observer<Boolean> {
                if (it == false) {
                    alertDialog.dismiss()
                }
            })
            alertDialog.setButton(-1, "Hủy") { dialog, which ->
                contactViewModel.callDel.cancel()
            }
            alertDialog.show()

        }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                data?.let {

                    val dataFirt = data.getStringExtra(AddContactActivity.DATA_FIRST)
                    val dataLast = data.getStringExtra(AddContactActivity.DATA_LAST)
                    val dataMail = data.getStringExtra(AddContactActivity.DATA_MAIL)

                    var dialog = AlertDialog.Builder(this).create()
                    dialog.setTitle("Load....")
                    contactViewModel.postContactVM(
                        ContactsPost(
                            ContactPost(
                                dataFirt,
                                dataLast,
                                dataMail.toString(),
                                Custom("xin chao")
                            )
                        )
                    )
                    contactViewModel.isBoolean.observe(this, Observer<Boolean> {
                        if (it == false) {
                            dialog.dismiss()
                        }
                    })
                    contactViewModel.resultAPI.observe(this, Observer<String> {
                        if (it == "errorPost"){
                            Toast.makeText(this,"Error !!",Toast.LENGTH_SHORT).show()
                        }
                    })

                    dialog.setButton(-1, "Hủy") { dialog, which ->
                        contactViewModel.callPost.cancel()
                    }
                    dialog.show()

                }
            }
        }
        if (requestCode == 9) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val dataFirt = data.getStringExtra(UpdateActivity.DATA_FIRST)
                    val dataLast = data.getStringExtra(UpdateActivity.DATA_LAST)
                    val dataMail = data.getStringExtra(UpdateActivity.DATA_MAIL)
                    var alertDialog = AlertDialog.Builder(this).create()
                    alertDialog.setTitle("Load....")
                    contactViewModel.postContactVM(
                        ContactsPost(
                            ContactPost(
                                dataFirt,
                                dataLast,
                                dataMail.toString(),
                                Custom("xin chao")
                            )
                        )
                    )
                    contactViewModel.isBoolean.observe(this, Observer<Boolean> {
                        if (it == false) {
                            alertDialog.dismiss()
                        }
                    })
                    alertDialog.setButton(-1, "Hủy") { dialog, which ->
                        contactViewModel.callPost.cancel()
                    }
                    alertDialog.show()
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
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position: Int = viewHolder.adapterPosition
//                binding.root.bt.visibility= View.VISIBLE
////                val callDel = mContact?.deletePost(mListContact[position].contactId!!)
////                callDel!!.enqueue(object : Callback<Contacts> {
////                    override fun onFailure(call: Call<Contacts>, t: Throwable) {
////                        Toast.makeText(
////                            applicationContext,
////                            "Error Delete : +${t.message}",
////                            Toast.LENGTH_LONG
////                        )
////                            .show()
////                        getList()
////                    }
////
////                    override fun onResponse(
////                        call: Call<Contacts>,
////                        response: Response<Contacts>
////                    ) {
////                        if (response.isSuccessful) {
////                            this@ListContactActivity.mListContact = response.body()!!.contacts
////
////                            Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
////                        }
////                    }
////                }
//                //)
//               // mListContact.removeAt(position)
//                mAdapter.notifyDataSetChanged()
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(myCallback)
//        itemTouchHelper.attachToRecyclerView(rcy_listContact)
//        mAdapter.setList(mListContact)
//    }
    }


}