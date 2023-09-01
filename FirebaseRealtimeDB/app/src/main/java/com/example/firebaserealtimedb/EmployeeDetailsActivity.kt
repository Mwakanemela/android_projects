package com.example.firebaserealtimedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaserealtimedb.databinding.ActivityEmployeeDetailsBinding
import com.example.firebaserealtimedb.databinding.ActivityFetchDataBinding

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var  binding:ActivityEmployeeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValuesTextViews()
    }

    private fun setValuesTextViews() {
        binding.tvEmpId.text = intent.getStringExtra("empId")
        binding.tvEmpAge.text = intent.getStringExtra("empAge")
        binding.tvEmpName.text = intent.getStringExtra("empName")
        binding.tvEmpSalary.text = intent.getStringExtra("empSalary")

    }
}