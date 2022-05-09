package com.assignment.zivame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.zivame.room.CartDatabase
import com.assignment.zivame.room.CartItems
import com.assignment.zivame.room.CartRepository
import com.assignment.zivame.ui.CartRecyclerViewAdapter
import com.assignment.zivame.ui.CartViewModel
import com.assignment.zivame.ui.CartViewModelFactory
import kotlinx.coroutines.job
import java.lang.Exception

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_view)

        val shippingCostArray = intArrayOf(0,30,35,40,45,50)

        val cartRepository = CartRepository(CartDatabase.invoke(this))
        val factory = CartViewModelFactory(cartRepository)

        val cartItemCount = findViewById<TextView>(R.id.cart_item_count)
        val cartTotalPrice = findViewById<TextView>(R.id.cart_total_price)
        val cartShippingCost = findViewById<TextView>(R.id.cart_shipping_cost)
        val cartGrandTotal = findViewById<TextView>(R.id.cart_grand_total)
        val cartCheckoutBtn = findViewById<Button>(R.id.checkout_cart_view_btn)
        val cartEmptyText = findViewById<TextView>(R.id.emptyCartView)

        val cartItemCountText = findViewById<TextView>(R.id.totalItemText)
        val cartTotalPriceText = findViewById<TextView>(R.id.totalPriceText)
        val cartShippingCostText = findViewById<TextView>(R.id.shippingText)
        val cartGrandTotalText = findViewById<TextView>(R.id.grandTotalText)
        val myCartText = findViewById<TextView>(R.id.myCartText)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val orderStatus = findViewById<TextView>(R.id.orderSuccess)
        val viewOverlay = findViewById<View>(R.id.view_overlay)

        val hr2 = findViewById<View>(R.id.hr2)
        val hr = findViewById<View>(R.id.hr)

        cartRecyclerView = findViewById<RecyclerView>(R.id.cart_recycler_view)

        orderStatus.visibility = View.INVISIBLE
        myCartText.visibility = View.VISIBLE
        hr.visibility = View.VISIBLE
        viewOverlay.visibility = View.INVISIBLE
        viewOverlay.alpha = 0.5f

        // Initialised View Model
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        val cartAdapter = CartRecyclerViewAdapter(listOf(), viewModel)
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.adapter = cartAdapter

        // To display all items in recycler view
        viewModel.allCartItems().observe(this, Observer {
            cartAdapter.list = it
            cartAdapter.notifyDataSetChanged()
        })

        viewModel.allCartItemsCount().observe(this) {
            cartItemCount.text = it.toString()
            if(it == 0){
                cartCheckoutBtn.visibility = View.INVISIBLE
                cartGrandTotal.visibility = View.INVISIBLE
                cartItemCount.visibility = View.INVISIBLE
                cartTotalPrice.visibility = View.INVISIBLE
                cartGrandTotal.visibility = View.INVISIBLE
                cartShippingCost.visibility = View.INVISIBLE

                cartItemCountText.visibility = View.INVISIBLE
                cartTotalPriceText.visibility = View.INVISIBLE
                cartShippingCostText.visibility = View.INVISIBLE
                cartGrandTotalText.visibility = View.INVISIBLE

                hr2.visibility = View.INVISIBLE

            } else{
                cartCheckoutBtn.visibility = View.VISIBLE
                cartGrandTotal.visibility = View.VISIBLE
                cartItemCount.visibility = View.VISIBLE
                cartTotalPrice.visibility = View.VISIBLE
                cartGrandTotal.visibility = View.VISIBLE
                cartShippingCost.visibility = View.VISIBLE

                cartItemCountText.visibility = View.VISIBLE
                cartTotalPriceText.visibility =View.VISIBLE
                cartShippingCostText.visibility =View.VISIBLE
                cartGrandTotalText.visibility =View.VISIBLE

                hr2.visibility = View.VISIBLE

                cartEmptyText.visibility = View.INVISIBLE
            }

        }

        viewModel.allCartItemsPrice().observe(this) {
            if(it == null){
                cartTotalPrice.text = "0 Rs"
            } else{
                val shippingCost = shippingCostArray[(0..5).random()]
                cartTotalPrice.text = "$it Rs"
                cartShippingCost.text = "$shippingCost Rs"
                cartGrandTotal.text = (it+shippingCost).toString() + " Rs"
            }
        }
        cartCheckoutBtn.setOnClickListener{

            Log.i("Progress bar","start")

            progressBar.visibility = View.VISIBLE
            viewOverlay.visibility = View.VISIBLE
            viewOverlay.bringToFront()
            Thread(Runnable{
                try {
                    //progressBar.progress
                    Thread.sleep(3000)
                    progressBar.visibility = View.INVISIBLE
                    viewModel.nukeTable()
                    runOnUiThread(Runnable {
                        cartEmptyText.visibility = View.INVISIBLE
                        viewOverlay.visibility = View.INVISIBLE
                        orderStatus.visibility = View.VISIBLE
                    })

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }).start()
            //progressBar.visibility = View.INVISIBLE
        }

    }

}