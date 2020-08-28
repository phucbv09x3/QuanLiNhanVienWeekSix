package com.monstar_lab_lifetime.quanlinhanvienweeksix.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Custom(
    @SerializedName(
        "string--Test--Field"
    )
    @Expose
    var string_Test_Field : String
)
{

}