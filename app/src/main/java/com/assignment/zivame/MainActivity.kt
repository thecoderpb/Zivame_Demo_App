package com.assignment.zivame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LinearLayoutManager(this)

        val gadgetsText = findViewById<TextView>(R.id.myCartText)
        val cartButton = findViewById<ImageView>(R.id.cartBtn)
        val cartBadge = findViewById<TextView>(R.id.badge)
        val hr = findViewById<View>(R.id.hr)
        val checkoutButton = findViewById<Button>(R.id.checkoutCentreButton)

        val errorText = findViewById<TextView>(R.id.errorText)
        val errorText2 = findViewById<TextView>(R.id.errorText2)
        val errorImageView = findViewById<ImageView>(R.id.errorImage)


        gadgetsText.visibility = View.INVISIBLE
        cartButton.visibility = View.INVISIBLE
        hr.visibility = View.INVISIBLE
        checkoutButton.visibility = View.INVISIBLE

        errorText.visibility = View.INVISIBLE
        errorText2.visibility = View.INVISIBLE
        errorImageView.visibility = View.INVISIBLE

        getDataFromAPI()

        cartButton.setOnClickListener{

            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        val cartItems = CartRepository(CartDatabase.invoke(this))
        val factory = CartViewModelFactory(cartItems)

        viewModel = ViewModelProvider(this@MainActivity, factory)[CartViewModel::class.java]
        viewModel.allCartItemsCount().observe(this){
            if(it == 1){
                cartBadge.text = it.toString()
                cartBadge.visibility = View.VISIBLE
                checkoutButton.visibility = View.VISIBLE
                checkoutButton.isEnabled = true
                checkoutButton.alpha = 0f;
                checkoutButton.translationY = 50F;
                checkoutButton.animate().alpha(1f).translationYBy(-50F).duration = 500;

            }else if (it == 0){
                checkoutButton.alpha = 1f;
                checkoutButton.translationY = -50F;
                checkoutButton.animate().alpha(0f).translationYBy(100F).duration = 750;
                checkoutButton.isEnabled = false
                cartBadge.visibility = View.INVISIBLE
            }else{
                cartBadge.text = it.toString()
                cartBadge.visibility = View.VISIBLE
            }

        }

        checkoutButton.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

     private fun getDataFromAPI(){
        Api.retrofitService.getDataFromAPI().enqueue(object: Callback<ResponseData> {
            val gadgetsText = findViewById<TextView>(R.id.myCartText)
            val cartButton = findViewById<ImageView>(R.id.cartBtn)
            val hr = findViewById<View>(R.id.hr)
            val checkoutButton = findViewById<Button>(R.id.checkoutCentreButton)

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
                                    //viewModel.getItemQuantity(item.itemName).observe(this@MainActivity){
                                    //    if(it >= 1){
                                    //        //viewModel.incrementProductQuantity(++item.itemQuantity,item.itemName)
                                    //    }else{
                                    //        viewModel.insert(item)
                                    //    }
                                    //}
                                    Log.i("Test","Item added to DB")

                                }
                            })
                        layoutManager = manager
                        adapter = productAdapter
                    }
                    gadgetsText.visibility = View.VISIBLE
                    cartButton.visibility = View.VISIBLE
                    hr.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
                Log.d("Failed Fetching content","null")
                val errorText = findViewById<TextView>(R.id.errorText)
                val errorText2 = findViewById<TextView>(R.id.errorText2)
                val errorImageView = findViewById<ImageView>(R.id.errorImage)

                errorText.visibility = View.VISIBLE
                errorText2.visibility = View.VISIBLE
                errorImageView.visibility = View.VISIBLE
            }
        })
    }
}