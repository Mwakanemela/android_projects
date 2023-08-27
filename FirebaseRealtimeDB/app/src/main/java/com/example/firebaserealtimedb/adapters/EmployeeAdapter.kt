package com.example.firebaserealtimedb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedb.EmployeeModel
import com.example.firebaserealtimedb.R

class EmployeeAdapter(private val employeeList:ArrayList<EmployeeModel>): RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_data_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.ViewHolder, position: Int) {
        val currentEmployee = employeeList[position]
        holder.employeeNameTv.text = currentEmployee.employeeName
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }


    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
            val employeeNameTv:TextView = itemView.findViewById(R.id.tvEmpName)
    }
}