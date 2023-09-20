package com.example.recyclerviewexampleone.nestedrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexampleone.R

class ParentAdapter(var parentList: List<ParentDataClass>): RecyclerView.Adapter<ParentAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val rvChild:RecyclerView = itemView.findViewById(R.id.rv_child)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.parent_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return parentList.size
    }

    fun applySearch(parentList: List<ParentDataClass>) {
        this.parentList = parentList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem= parentList[position]

        holder.tvTitle.text = currentItem.title
        holder.rvChild.setHasFixedSize(true)
        holder.rvChild.layoutManager = GridLayoutManager(holder.itemView.context, 1, RecyclerView.HORIZONTAL, false)

        val adapter = ChildAdapter(currentItem.childList)
        holder.rvChild.adapter = adapter
    }
}