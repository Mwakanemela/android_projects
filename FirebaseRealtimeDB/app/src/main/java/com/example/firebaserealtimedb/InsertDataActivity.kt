package com.example.firebaserealtimedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaserealtimedb.databinding.ActivityInsertDataBinding
import com.example.firebaserealtimedb.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertDataBinding

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertDataBinding.inflate(layoutInflater);
        setContentView(binding.root);

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        binding.btnSave.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        val employeeName = binding.etEmpName.text.toString()
        val employeeAge = binding.etEmpAge.text.toString()
        val employeeSalary = binding.etEmpSalary.text.toString()

        if(employeeName.isEmpty()) {
            binding.etEmpName.error = "Please enter Name"
        }

        if(employeeAge.isEmpty()) {
            binding.etEmpAge.error = "Please enter Age"
        }

        if(employeeSalary.isEmpty()) {
            binding.etEmpSalary.error = "Please enter Salary"
        }

        val employeeId = dbRef.push().key

        val employee = EmployeeModel(employeeId, employeeName, employeeAge, employeeSalary)
        dbRef.child(employeeId!!).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()
                binding.etEmpAge.text.clear()
                binding.etEmpName.text.clear()
                binding.etEmpSalary.text.clear()

            }.addOnFailureListener { err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}