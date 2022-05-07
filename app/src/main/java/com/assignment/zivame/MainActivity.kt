package com.assignment.zivame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

//API : https://my-json-server.typicode.com/nancymadan/assignment/db
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
    }

    private fun getDataFromAPI(){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com")
            .build()

        val service = retrofit.create(APIService::class.java)
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.getProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()?.string()
                        )
                    )
                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}