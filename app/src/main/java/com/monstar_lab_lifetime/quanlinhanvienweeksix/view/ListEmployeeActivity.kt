package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.EmployeeRepository
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.OnItemClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter.EmployeeAdapter
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.*
import kotlinx.android.synthetic.main.activity_list_employee.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ListEmployeeActivity : AppCompatActivity(), OnItemClick {
    private var mAdapter = EmployeeAdapter(this)
    private var mRetrofit: Retrofit? = null
    private var mEmployeeRepository: EmployeeRepository? = null
    private var mListEmployee = mutableListOf<Employee>()
    private val REQUES_CODE = 0
    private val REQUES_CODET = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_employee)
        rcy_listEmployee.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRetrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mEmployeeRepository = mRetrofit?.create(EmployeeRepository::class.java)
        rcy_listEmployee.adapter = mAdapter
        getList()
        btn_add.setOnClickListener {
            val intentAdd = Intent(this, AddEmployeeActivity::class.java)
            startActivityForResult(intentAdd, REQUES_CODE)
        }

        delete()

    }
    fun delete(){
        val myCallback = object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position:Int=viewHolder.adapterPosition
                mListEmployee.removeAt(position)
                mAdapter.notifyDataSetChanged()

                
                var callDel=mEmployeeRepository?.deletePost(mListEmployee[position].id)
                callDel?.enqueue(object :Callback<APIResponePost>{
                    override fun onFailure(call: Call<APIResponePost>, t: Throwable) {
                        Toast.makeText(applicationContext,"Error Delete : +$t",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<APIResponePost>,
                        response: Response<APIResponePost>
                    ) {
                        if (response?.isSuccessful==true){
                            Log.d("t",response.code().toString())
                            Toast.makeText(applicationContext,response.body().toString(),Toast.LENGTH_LONG).show()
                        }
                    }

                })
            }

        }

        val itemTouchHelper = ItemTouchHelper(myCallback)
        itemTouchHelper.attachToRecyclerView(rcy_listEmployee)
    }




    // http://dummy.restapiexample.com/api/v1/employees
    fun getList() {
        var call = mEmployeeRepository?.getListEmployee()
        call?.enqueue(object : Callback<APIResponse> {
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error+$t", Toast.LENGTH_LONG).show()
                mAdapter.setList(ArrayList<Employee>())
            }

            override fun onResponse(
                call: Call<APIResponse>,
                response: Response<APIResponse>
            ) {
                if (response?.isSuccessful == true) {

                    val list = response.body()?.data
                    this@ListEmployeeActivity.mListEmployee=list!!
                    // Toast.makeText(applicationContext, list.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(
                        applicationContext,
                        response.body()?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()

                    Log.d("Tag", list.toString())
                    list?.let {
                        this@ListEmployeeActivity.mListEmployee=it
                        mAdapter.setList(it)

                    }
                }

            }


        })
    }



    override fun OnItemClick(employee: Employee, position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("KEY1", employee.id)
        bundle.putString("KEY2", employee.employee_names)
        bundle.putInt("KEY3", employee.employee_ages)
        bundle.putLong("KEY4", employee.employee_salarys)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUES_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val dataId = data.getStringExtra(AddEmployeeActivity.DATA_ID).toInt()
                    val dataName = data.getStringExtra(AddEmployeeActivity.DATA_NAME)
                    val dataAge = data.getStringExtra(AddEmployeeActivity.DATA_AGE).toInt()
                    val dataSalary = data.getStringExtra(AddEmployeeActivity.DATA_SALARY).toLong()
                    Toast.makeText(applicationContext,dataName,Toast.LENGTH_LONG).show()
                   // mEmployeeRepository = mRetrofit?.create(EmployeeRepository::class.java)
                    var callPost = mEmployeeRepository?.postEmployee(
                        data = EmployPost(
                           name =  dataName,
                           age =  dataAge,
                            salary = dataSalary
                        )
                    )
                    callPost?.enqueue(object : Callback<APIResponePost> {
                        override fun onFailure(call: Call<APIResponePost>, t: Throwable) {

                            Toast.makeText(
                                applicationContext,
                                "Error+${t.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<APIResponePost>,
                            response: Response<APIResponePost>
                        ) {
                            if (response.isSuccessful == true) {
                                Toast.makeText(
                                    applicationContext,
                                  response.body()!!.toString(),
                                    Toast.LENGTH_LONG
                                ).show()

                                Log.d(
                                    "okp", response.code().toString()
                                )
                                Log.d("p", response.body().toString())
                            }
                        }

                    })

                }


            }
        }
    }


}