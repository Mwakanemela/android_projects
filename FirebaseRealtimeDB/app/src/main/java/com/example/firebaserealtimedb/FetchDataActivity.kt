package com.example.firebaserealtimedb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaserealtimedb.adapters.EmployeeAdapter
import com.example.firebaserealtimedb.databinding.ActivityFetchDataBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchDataActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFetchDataBinding
    private lateinit var employeeList:ArrayList<EmployeeModel>
    private lateinit var dbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityFetchDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEmp.layoutManager = LinearLayoutManager(this)
        binding.rvEmp.setHasFixedSize(true)
        employeeList = arrayListOf<EmployeeModel>()

        getEmployeesData()

    }

    private fun getEmployeesData() {
        binding.rvEmp.visibility = View.GONE
        binding.tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                employeeList.clear()
                if (snapshot.exists()) {
                    //snapshot means data
                    for(employeeSnapshot in snapshot.children) {
                        val employeeData = employeeSnapshot.getValue(EmployeeModel::class.java)
                        employeeList.add(employeeData!!)
                    }

                    val mAdapter = EmployeeAdapter(employeeList)
                    binding.rvEmp.adapter = mAdapter
                    mAdapter.setOnItemClickListener(object : EmployeeAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchDataActivity, EmployeeDetailsActivity::class.java)
                            intent.putExtra("empId", employeeList[position].employeeId)
                            intent.putExtra("empName", employeeList[position].employeeName)
                            intent.putExtra("empAge", employeeList[position].employeeAge)
                            intent.putExtra("empSalary", employeeList[position].employeeSalary)
                            startActivity(intent)
                        }

                    })
                    binding.rvEmp.visibility = View.VISIBLE
                    binding.tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}