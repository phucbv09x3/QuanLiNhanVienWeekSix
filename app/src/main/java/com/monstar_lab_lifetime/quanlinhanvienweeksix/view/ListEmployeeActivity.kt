package com.monstar_lab_lifetime.quanlinhanvienweeksix.view

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.EmployeeRepository
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.OnItemClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter.EmployeeAdapter
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.APIResponse
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Employee
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_list_employee.*
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ListEmployeeActivity : AppCompatActivity(), OnItemClick {
    private var mAdapter = EmployeeAdapter(this)
    private var mRetrofit: Retrofit? = null
    private var service: EmployeeRepository? = null
    private var mListEmployee = mutableListOf<Employee>()
    private val REQUES_CODE=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_employee)
        rcy_listEmployee.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRetrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = mRetrofit?.create(EmployeeRepository::class.java)
        rcy_listEmployee.adapter = mAdapter
        getList()
        btn_add.setOnClickListener {
            val intentAdd = Intent(this, AddEmployeeActivity::class.java)
            startActivityForResult(intentAdd, REQUES_CODE)
        }


    }


    // http://dummy.restapiexample.com/api/v1/employees
    fun getList() {
        val call = service?.getListEmployee()
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
                    Toast.makeText(applicationContext, list.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(applicationContext, response.body()?.message.toString(), Toast.LENGTH_LONG).show()

                    Log.d("Tag", list.toString())
                    list?.let {
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
                    val callPost = service?.postEmployee(dataName, dataAge, dataSalary)
                        ?.enqueue(object : Callback<APIResponse> {
                            override fun onFailure(call: Call<APIResponse>, t: Throwable) {

                                Toast.makeText(this@ListEmployeeActivity,t.toString(),Toast.LENGTH_LONG).show()
                            }

                            override fun onResponse(
                                call: Call<APIResponse>,
                                response: Response<APIResponse>
                            ) {
                                if (response.isSuccessful == true) {
                                    Toast.makeText(
                                        this@ListEmployeeActivity,
                                        response.body()?.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    Log.d("p",response.body().toString())
                                }
                            }

                        })
                   // val lisssst= mutableListOf(dataId,dataName,dataAge,dataSalary) as MutableList<Employee>
                   mListEmployee.add(Employee(dataId,dataName,dataAge,dataSalary))
                    mAdapter.setList(mListEmployee)
                }



            }
        }
    }


}