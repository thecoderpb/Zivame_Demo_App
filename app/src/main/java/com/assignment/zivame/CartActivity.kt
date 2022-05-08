package com.assignment.zivame

import android.os.Bundle
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

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var cartAdapter: RecyclerView.Adapter<*>
    lateinit var viewModel: CartViewModel
    lateinit var list: List<CartItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_view)

        val cartRepository = CartRepository(CartDatabase.invoke(this))
        val factory = CartViewModelFactory(cartRepository)

        cartRecyclerView = findViewById<RecyclerView>(R.id.cart_recycler_view)
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

    }
}