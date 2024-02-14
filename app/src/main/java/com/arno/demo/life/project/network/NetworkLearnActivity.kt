package com.arno.demo.life.project.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arno.demo.life.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkLearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_learn)
        request()
    }

    fun request() {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.baidu.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(BaiduService::class.java)
        val call = service.getHomePage()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // TODO: Handle the response body
                    println(response.body())
                } else {
                    // TODO: Handle the error
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // TODO: Handle the failure
                println("Failure: ${t.message}")
            }
        })
    }
}

interface BaiduService {
    @GET("/")
    fun getHomePage(): Call<String>
}