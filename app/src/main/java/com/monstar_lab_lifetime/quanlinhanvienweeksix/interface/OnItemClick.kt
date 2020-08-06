package com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`

import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Employee

interface OnItemClick {
    fun OnItemClick(employee: Employee,position:Int)
}