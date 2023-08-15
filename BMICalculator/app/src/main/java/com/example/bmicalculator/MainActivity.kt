package com.example.bmicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val calcButton = findViewById<Button>(R.id.calculateBtn)

        calcButton.setOnClickListener {
            val height = heightText.text.toString()
            val weight = weightText.text.toString()

            if(validateInput(weight, height)) {

                val bmi = weight.toFloat()/((height.toFloat()/100)*(height.toFloat()/100))
                //to 2 decimal places
                val bmi2Digits = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2Digits)
            }

        }
    }

    //check for input fields
    private fun validateInput(weight:String?, height:String):Boolean {

        return when{
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is empty", Toast.LENGTH_SHORT).show()
                return false
            }

            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is empty", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> {
                return true
            }
        }
    }
    private  fun displayResult(bmi:Float) {
        val resultIndex = findViewById<TextView>(R.id.tvIndex)
        val resultDescription = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvInfo)

        resultIndex.text = bmi.toString()
        info.text = "(Note: normal range is 18.25 - 24.0)"

        var resultText = ""
        var color = 0

        when{
            bmi < 18.50 -> {
                resultText = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.50..24.99 -> {
                resultText = "Healthy"
                color = R.color.normal_weight
            }
            bmi in 25.00 .. 29.99 -> {
                resultText = "Overweight"
                color = R.color.over_weight
            }
            bmi > 29.99 -> {
                resultText = "Obese"
                color = R.color.obese_weight
            }
        }

        resultDescription.setTextColor(ContextCompat.getColor(this, color))
        resultDescription.text = resultText
    }
}