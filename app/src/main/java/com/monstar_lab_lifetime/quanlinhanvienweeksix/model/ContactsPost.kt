package com.monstar_lab_lifetime.quanlinhanvienweeksix.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContactsPost(
    @SerializedName("contact")
    @Expose
    var contact: ContactPost
) {
}