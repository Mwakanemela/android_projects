package com.example.recyclerviewexampleone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val softwareList:ArrayList<Software>):
RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()
{
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.imageTitle)
        val nameTv = itemView.findViewById<TextView>(R.id.tvName)
        val description = itemView.findViewById<TextView>(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return softwareList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = softwareList[position]

        holder.description.text = currentItem.description
        holder.image.setImageResource(currentItem.imageTitle)
        holder.nameTv.text = currentItem.name
    }
}