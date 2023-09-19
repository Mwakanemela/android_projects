package com.example.recyclerviewexampleone.nestedrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexampleone.R

class ChildAdapter(val childList: List<ChildDataClass>):RecyclerView.Adapter<ChildAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        val imgChild:ImageView = itemView.findViewById(R.id.img_child)
        val cl:ConstraintLayout = itemView.findViewById(R.id.cl)
        val cv:CardView = itemView.findViewById(R.id.cv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.child_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem= childList[position]

        holder.imgChild.setImageResource(currentItem.image)
        holder.cv.startAnimation(
            AnimationUtils.loadAnimation(
            holder.cv.context, R.anim.scale_up
        ))
    }
}