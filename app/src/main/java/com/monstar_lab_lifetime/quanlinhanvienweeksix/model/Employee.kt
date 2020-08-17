package com.monstar_lab_lifetime.quanlinhanvienweeksix.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Employee(
   @SerializedName("id")
    var id: Int,

    @SerializedName("employee_name")
    var employee_names: String,

    @SerializedName("employee_age")
    var employee_ages: Int,

    @SerializedName("employee_salary")
    var employee_salarys: Long
) {
}