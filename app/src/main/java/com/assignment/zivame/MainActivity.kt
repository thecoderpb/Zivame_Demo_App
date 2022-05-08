package com.assignment.zivame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.zivame.api.Api
import com.assignment.zivame.api.ResponseData
import com.assignment.zivame.room.CartDatabase
import com.assignment.zivame.room.CartItems
import com.assignment.zivame.room.CartRepository
import com.assignment.zivame.ui.CartViewModel
import com.assignment.zivame.ui.CartViewModelFactory
import com.assignment.zivame.ui.ProductAdapter
import com.assignment.zivame.util.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//API : https://my-json-server.typicode.com/nancymadan/assignment/db
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var productAdapter: RecyclerView.Adapter<*>
    lateinit var viewModel: CartViewModel
    lateinit var list: List<CartItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = LinearLayoutManager(this)
        getAllData()
        val cartBtn : ImageView = findViewById(R.id.cartBtn)
        cartBtn.setOnClickListener{

            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

    }

     private fun getAllData(){
        Api.retrofitService.getDataFromAPI().enqueue(object: Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if(response.isSuccessful){

                    recyclerView = findViewById<RecyclerView>(R.id.product_recycler_view).apply{
                        Log.d("API Content",response.body().toString())

                        productAdapter = ProductAdapter(response.body()!!.products,
                            object : ItemClickListener {
                                override fun onItemClickListener(item: CartItems) {
                                    val cartItems = CartRepository(CartDatabase.invoke(context))
                                    val factory = CartViewModelFactory(cartItems)

                                    viewModel = ViewModelProvider(this@MainActivity, factory)[CartViewModel::class.java]
                                    viewModel.insert(item)
                                    Log.i("Test","Item added to DB")

                                }
                            })
                        layoutManager = manager
                        adapter = productAdapter
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