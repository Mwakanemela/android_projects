package com.example.firebaserealtimedb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedb.EmployeeModel
import com.example.firebaserealtimedb.R

class EmployeeAdapter(private val employeeList:ArrayList<EmployeeModel>): RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_data_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.ViewHolder, position: Int) {
        val currentEmployee = employeeList[position]
        holder.employeeNameTv.text = currentEmployee.employeeName
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    class ViewHolder(itemView:View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
            val employeeNameTv:TextView = itemView.findViewById(R.id.tvEmpName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}