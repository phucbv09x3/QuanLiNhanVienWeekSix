package com.monstar_lab_lifetime.quanlinhanvienweeksix.model

import com.google.gson.annotations.SerializedName

data class EmployPost(
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int,
    @SerializedName("salary") val salary: Long
) {
}