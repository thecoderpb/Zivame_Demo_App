package com.assignment.zivame.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.zivame.R
import com.assignment.zivame.room.CartItems
import com.bumptech.glide.Glide
import java.util.logging.Handler

class CartRecyclerViewAdapter(var list: List<CartItems>, private val viewModel: CartViewModel) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

    private var view : View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.cart_card_view, parent, false)
        return CartViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentPosition = list[position]
        holder.itemView.findViewById<TextView>(R.id.itemName).text = currentPosition.itemName
        holder.itemView.findViewById<TextView>(R.id.itemPrice).text = "\u20B9"+"${currentPosition.itemPrice}"
        holder.itemView.findViewById<TextView>(R.id.itemQuantity).text = "Quantity:${currentPosition.itemQuantity}"
        //holder.itemView.findViewById<ImageView>(R.id.itemImageView).setImageURI(list[position].itemImageURL)
        Glide.with(view!!.context).load(currentPosition.itemImageURL).centerCrop().into(view!!.findViewById(R.id.itemImageView))
        holder.itemView.findViewById<ImageView>(R.id.itemDelete).setOnClickListener {
            viewModel.delete(currentPosition)
        }
    }
    // Inner class for viewHolder
    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}