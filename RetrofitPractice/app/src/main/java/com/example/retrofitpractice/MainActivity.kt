package com.example.retrofitpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.retrofitpractice.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            progressBar.visibility = View.VISIBLE
            tvBody.visibility = View.GONE
            tvId.visibility = View.GONE
            tvTitle.visibility = View.GONE
            tvUserId.visibility = View.GONE
        }

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getPosts()
            }catch (e : HttpException) {
                Toast.makeText(applicationContext,"http error ${e.message}",Toast.LENGTH_LONG).show()
                return@launch
            }catch (e: IOException) {
                Toast.makeText(applicationContext,"app error ${e.message}",Toast.LENGTH_LONG).show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        tvBody.visibility = View.GONE
                        tvId.visibility = View.GONE
                        tvTitle.visibility = View.GONE
                        tvUserId.visibility = View.GONE

                        tvId.text = "id: ${ response.body()!!.id}"
                        tvUserId.text = "user id: ${ response.body()!!.userId}"
                        tvTitle.text = "Title: ${response.body()!!.title}"
                        tvBody.text = "Body ${response.body()!!.body}"
                    }
                }
            }
        }
    }
}