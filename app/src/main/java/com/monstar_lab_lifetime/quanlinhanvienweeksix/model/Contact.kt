package com.monstar_lab_lifetime.quanlinhanvienweeksix.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.CustomField
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


data class Contact(
    @SerializedName("Email")
    @Expose
     var email: String,
    @SerializedName("created_at")
    @Expose
     var createdAt: String,
    @SerializedName("updated_at")
    @Expose
     var updatedAt: String,
    @SerializedName("api_originated")
    @Expose
     var apiOriginated: Boolean,
    @SerializedName("custom_fields")
    @Expose
     var customFields: MutableList<CustomField>,
    @SerializedName("Name")
    @Expose
     var name: String,
    @SerializedName("LastName")
    @Expose
     var lastName: String,
    @SerializedName("FirstName")
    @Expose
     var firstName: String,
    @SerializedName("contact_id")
    @Expose
     var contactId: String
) {
}