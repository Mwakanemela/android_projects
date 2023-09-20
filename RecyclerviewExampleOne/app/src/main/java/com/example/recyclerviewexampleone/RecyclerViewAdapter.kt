package com.example.recyclerviewexampleone

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val softwareList:ArrayList<Software>):
RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()
{
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.imageTitle)
        val nameTv = itemView.findViewById<TextView>(R.id.tvName)
        val description = itemView.findViewById<TextView>(R.id.tvDescription)
        val itemRow: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
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

        val context = holder.itemRow.context
        holder.itemRow.setOnClickListener {
            Toast.makeText(context, "${currentItem.name} clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(it.context, SecondActivity::class.java)
            intent.putExtra("name", currentItem.name)
            intent.putExtra("desc", currentItem.description)
            intent.putExtra("image", currentItem.imageTitle)
            context.startActivity(intent)
        }

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(
            holder.cardView.context, R.anim.scale_up
        ))
        holder.itemRow.setOnLongClickListener(View.OnLongClickListener {
            Toast.makeText(context, "${currentItem.name} long clicked", Toast.LENGTH_SHORT).show()
            return@OnLongClickListener true
        })
    }
}