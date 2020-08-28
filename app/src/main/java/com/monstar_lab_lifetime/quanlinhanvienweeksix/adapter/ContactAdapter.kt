package com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.OnItemClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.`interface`.OnLongClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact

class ContactAdapter(val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    var onLongClick: OnLongClick? = null
    private var mListContact: MutableList<Contact> = mutableListOf()
    fun setList(mListContact: MutableList<Contact>) {
        this.mListContact = mListContact
        Log.d("tag1", mListContact.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListContact.size
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactViewHolder, position: Int) {
        val contact = mListContact[position]

        holder.name.text = (contact.lastName)
        holder.mail.text = contact.email
        holder.itemView.setOnClickListener {
            onItemClick.OnItemClick(contact, position)
        }


        holder.itemView.setOnLongClickListener {
            onItemClick.onLongClick(contact, position)
            return@setOnLongClickListener true
        }
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById(R.id.tv_showNameCt) as TextView
        val mail = itemView.findViewById<TextView>(R.id.tv_showMailct)


    }
}