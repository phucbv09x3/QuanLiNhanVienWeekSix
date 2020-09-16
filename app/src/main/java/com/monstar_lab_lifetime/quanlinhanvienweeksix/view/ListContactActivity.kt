package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    OnItemClick, View.OnClickListener {
    // var isLoading = ObservableBoolean(false)
    private var mAdapter = ContactAdapter(this)
    private var mRetrofit: Retrofit? = null
    private var mContact: APIContactRepository? = null
    private var mListContact = mutableListOf<Contact>()

    var mListGet = mutableListOf<Contact>()

    companion object {
        private const val REQUEST_CODE = 0
    }

    private var mTextError: String = ""
    var data = MutableLiveData<MutableList<Contact>>()
    lateinit var binding: ActivityListContactBinding
    lateinit var contactViewModel: ContactViewModel

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
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
        reLoad()
        btn_search.setOnClickListener(this)


    }
//    fun tik(){
//        //contactViewModel.search(edt_text.text.toString().trim())
//        contactViewModel.contactNew.observe(this, Observer<MutableList<Contact>> {
//            it?.let {
//                (binding.rcyListContact.adapter as ContactAdapter).setList(it)
//            }
//        })
//
//    }
    private fun reLoad(){
        mSwipeRefreshLayout = findViewById(R.id.sw_reload)
        mSwipeRefreshLayout.setOnRefreshListener {
            mSwipeRefreshLayout.post(object : Runnable {
                override fun run() {
                    (binding.rcyListContact.adapter as ContactAdapter).setList(mListGet)
                    mSwipeRefreshLayout.isRefreshing = false
                }

            })
        }
    }

    private fun searchName() {
        var mutableListCreateNew = mutableListOf<Contact>()
        mListGet.forEachIndexed { index, contact ->
            if (mListGet[index].lastName.equals(edt_text.text.toString().trim())) {
                mutableListCreateNew?.add(
                    Contact(
                        mListGet[index].email,
                        mListGet[index].createdAt,
                        mListGet[index].updatedAt,
                        mListGet[index].apiOriginated,
                        mListGet[index].customFields,
                        mListGet[index].name,
                        mListGet[index].lastName,
                        mListGet[index].firstName,
                        mListGet[index].contactId
                    )
                )
            }
            (binding.rcyListContact.adapter as ContactAdapter).setList(mutableListCreateNew)
        }
    }

    private fun getList() {

        var alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Loading")
        alertDialog.setMessage("Vui lòng chờ...")

//        alertDialog.setButton(-1,"Ẩn thông báo này") {dialog: DialogInterface?, which: Int ->
//           //-1 là định danh của  DialogInterface.BUTTON_POSITIVE
//        }
        alertDialog.show()
        contactViewModel.getContactVM()
        contactViewModel.contact.observe(this, Observer<MutableList<Contact>> {
            it?.let {
                this.mListGet = it
                (binding.rcyListContact.adapter as ContactAdapter).setList(it)
            }
        })
        contactViewModel.resultAPI.observe(this, Observer<String> {
            if (it == "errorGetList") {
                Toast.makeText(this, "Lỗi cập nhật ! Vui lòng thử lại sau !!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        contactViewModel.isBoolean.observe(this, Observer<Boolean> {
            if (it == false) {
                alertDialog.dismiss()
            }
        })


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
        dialog.setTitle("Bạn muốn chỉnh sửa hay xóa!")
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
            contactViewModel.resultAPI.observe(this, Observer<String> {
                if (it == "errorDelete") {
                    Toast.makeText(this, "Lỗi ! Vui lòng thử lại sau !!", Toast.LENGTH_SHORT)
                        .show()
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
                        if (it == "errorPost") {
                            Toast.makeText(
                                this,
                                "Lỗi  ! Vui lòng thử lại sau !!",
                                Toast.LENGTH_SHORT
                            ).show()
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
                    contactViewModel.resultAPI.observe(this, Observer<String> {
                        if (it == "errorPost") {
                            Toast.makeText(
                                this,
                                "Lỗi  ! Vui lòng thử lại sau !!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                    alertDialog.setButton(-1, "Hủy") { dialog, which ->
                        contactViewModel.callPost.cancel()
                    }
                    alertDialog.show()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_search -> {
                searchName()
                //tik()
            }
        }
    }


}