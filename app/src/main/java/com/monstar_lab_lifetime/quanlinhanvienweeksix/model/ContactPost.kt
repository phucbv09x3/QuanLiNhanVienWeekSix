package com.monstar_lab_lifetime.quanlinhanvienweeksix.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContactPost(
    @SerializedName("FirstName")
    @Expose
     var firstName: String,
    @SerializedName("LastName")
    @Expose
     var lastName: String,
    @SerializedName("Email")
    @Expose
     var email: String,
    @SerializedName("custom")
    @Expose
     var custom: Custom
//    @SerializedName("contact_id")
//    @Expose
//    private var contactId: String
) {
}