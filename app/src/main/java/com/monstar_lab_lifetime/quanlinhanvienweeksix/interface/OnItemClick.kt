package com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`

import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact

interface OnItemClick {
    fun OnItemClick(contact: Contact, position:Int)
    fun onLongClick(contact: Contact, position:Int)
}