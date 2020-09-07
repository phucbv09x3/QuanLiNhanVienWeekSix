package com.monstar_lab_lifetime.quanlinhanvienweeksix.Interface

import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact

interface OnItemClick {
    fun onItemClick(contact: Contact, position:Int)
    fun onLongClick(contact: Contact, position:Int)
}