package com.assignment.zivame.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.zivame.api.ProductProperty
import com.assignment.zivame.R
import com.assignment.zivame.room.CartDatabase
import com.assignment.zivame.room.CartItems
import com.assignment.zivame.room.CartRepository
import com.assignment.zivame.util.ItemClickListener
import com.bumptech.glide.Glide
import kotlinx.coroutines.withContext

class ProductAdapter(private val data: List<ProductProperty>, private val buttonClickListener : ItemClickListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()  {

    class ProductViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bind(property: ProductProperty){
            val productName = view.findViewById<TextView>(R.id.productName)
            val productImage = view.findViewById<ImageView>(R.id.productImageView)
            val productPrice = view.findViewById<TextView>(R.id.productPrice)
            val productRating = view.findViewById<TextView>(R.id.productRating)
            //val addProductToDB = view.findViewById<Button>(R.id.addProductButton)
            //val addToCartDB = CartItems(property.name!!,property.name,1,property.price!!.toInt(),property.image_url!!)

            productName.text = property.name
            productPrice.text = "\u20B9" + property.price.toString()
            productRating.text = "Rating: " + property.rating.toString()

            Glide.with(view.context).load(property.image_url).centerCrop().into(productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_card_view, parent, false)
        return ProductViewHolder(v)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])
        val addToCartDB = CartItems(data[position].name!!,data[position].name!!,1,data[position].price!!.toInt(),data[position].image_url!!)
        holder.itemView.findViewById<Button>(R.id.addProductButton).setOnClickListener{
            buttonClickListener.onItemClickListener(addToCartDB)
            Log.i("Button Clicked " ,addToCartDB.toString())
        }
    }

}
