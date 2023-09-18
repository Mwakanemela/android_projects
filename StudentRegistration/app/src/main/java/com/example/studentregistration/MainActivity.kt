package com.example.studentregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.studentregistration.databinding.ActivityMainBinding
import com.example.studentregistration.db.Student
import com.example.studentregistration.db.StudentDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: StudentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        binding.saveBtn.setOnClickListener {
            saveStudentData()
            clearInput()
        }

        binding.clearBtn.setOnClickListener {
            clearInput()
        }

    }

    private fun saveStudentData() {
        viewModel.insertStudent(
            Student(
                0,
                binding.etName.toString(),
                binding.etEmail.toString()
            )
        )
    }

    private fun clearInput() {
        binding.etEmail.setText("")
        binding.etName.setText("")
    }
}