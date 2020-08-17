package com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`

import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.APIResponePost
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.APIResponse
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.EmployPost
import retrofit2.http.*


interface EmployeeRepository {
    @GET("employees/")
    fun getListEmployee(): retrofit2.Call<APIResponse>

    @POST("create")
    fun postEmployee(
        @Body
        data: EmployPost
    ): retrofit2.Call<APIResponePost>
    @DELETE("delete/{id}")
    fun deletePost(@Path("id") id: Int): retrofit2.Call<APIResponePost>

}