package com.monstar_lab_lifetime.quanlinhanvienweeksix.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monstar_lab_lifetime.quanlinhanvienweeksix.Interface.OnItemClick
import com.monstar_lab_lifetime.quanlinhanvienweeksix.databinding.ItemContactBinding
import com.monstar_lab_lifetime.quanlinhanvienweeksix.model.Contact
import java.util.concurrent.CopyOnWriteArrayList

class ContactAdapter(val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var mListContact: MutableList<Contact> = mutableListOf()
    fun setList(mListContact: MutableList<Contact>) {
        this.mListContact = mListContact
        sortContactByLastName(mListContact)
        notifyDataSetChanged()
    }
    fun sortContactByLastName(mListContact: MutableList<Contact>){
        this.mListContact=mListContact
        mListContact?.let {
            it.sortWith(
                compareBy { it!!.lastName.toLowerCase() }
            )
        }
    }

    fun clear(mListContact: MutableList<Contact>){
        mListContact.clear()
        this.mListContact=mListContact
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val binding=ItemContactBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mListContact.size
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactViewHolder, position: Int) {
        var contact=mListContact[position]
       holder.binding.itemContact=contact
        holder.binding.root.setOnClickListener {
            onItemClick.onItemClick(contact,holder.adapterPosition)
        }
        holder.binding.root.setOnLongClickListener {
            onItemClick.onLongClick(contact,holder.adapterPosition)
            return@setOnLongClickListener true
        }
//        holder.binding.item.setOnLongClickListener {
//            onItemClick.onLongClick(contact,holder.adapterPosition)
//            return@setOnLongClickListener true
//        }
//        holder.itemView.setOnClickListener {
//            onItemClick.onItemClick(contact, position)
//        }
//
//        holder.itemView.setOnLongClickListener {
//            onItemClick.onLongClick(contact, position)
//            return@setOnLongClickListener true
//        }
    }

    class ContactViewHolder : RecyclerView.ViewHolder{
        val binding :ItemContactBinding
        constructor(binding: ItemContactBinding) : super(binding.root) {
            this.binding = binding
        }

    }
}