package com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`

import android.telecom.Call
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.APIResponse
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Employee
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface EmployeeRepository {
    @GET("/api/v1/employees")
    fun getListEmployee()  :retrofit2.Call<APIResponse>

    @POST("/api/v1/create")
    fun postEmployee(@Field("name") employee_name:String,
    @Field("age") employee_age:Int,
    @Field("salary") employee_salary:Long): retrofit2.Call<APIResponse>

}