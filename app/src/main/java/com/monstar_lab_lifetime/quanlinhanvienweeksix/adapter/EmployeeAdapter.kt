package com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.OnItemClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Employee

class EmployeeAdapter(val onItemClick:OnItemClick) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    private var mListEmployee: MutableList<Employee> = mutableListOf()
    fun setList(mLisst: MutableList<Employee>){
        this.mListEmployee=mLisst
        Log.d("tag1",mLisst.toString())
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employee,parent,false)
        return EmployeeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListEmployee.size
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.EmployeeViewHolder, position: Int) {
        val employee=mListEmployee[position]
        holder.postName.text=employee.employee_names
        holder.postAge.text=employee.employee_ages.toString()
        holder.postSalary.text=employee.employee_salarys.toString()
        holder.itemView.setOnClickListener {
            onItemClick.OnItemClick(employee,position)
        }

    }

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postName = itemView.findViewById(R.id.tv_showNameEm) as TextView
        val postAge = itemView.findViewById(R.id.tv_showAgeEm) as TextView
        val postSalary = itemView.findViewById(R.id.tv_showSalaryEm) as TextView
    }
}