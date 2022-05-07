package com.assignment.zivame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//API : https://my-json-server.typicode.com/nancymadan/assignment/db
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = LinearLayoutManager(this)
        getAllData()

    }

    private fun getAllData(){
        Api.retrofitService.getDataFromAPI().enqueue(object: Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if(response.isSuccessful){

                    recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply{
                        Log.d("API Content",response.body().toString())

                        myAdapter = MyAdapter(response.body()!!.products)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
                Log.d("Failed Fetching content","null")
            }
        })
    }
}