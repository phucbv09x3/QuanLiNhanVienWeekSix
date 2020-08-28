package com.monstar_lab_lifetime.quanlinhanvienweeksix.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CustomField(
    @SerializedName("kind")
    @Expose
    private var kind: String,
    @SerializedName("value")
    @Expose
    private var value: String,
    @SerializedName("fieldType")
    @Expose
    private var fieldType: String,
    @SerializedName("deleted")
    @Expose
    private var deleted: Boolean
) {
}