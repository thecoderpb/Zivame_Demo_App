package com.assignment.zivame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val data: List<ProductProperty>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()  {

    class MyViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bind(property: ProductProperty){
            val productName = view.findViewById<TextView>(R.id.productName)
            val productImage = view.findViewById<ImageView>(R.id.productImageView)
            val productPrice = view.findViewById<TextView>(R.id.productPrice)
            val productRating = view.findViewById<TextView>(R.id.productRating)

            productName.text = property.name
            productPrice.text = "\u20B9" + property.price.toString()
            productRating.text = "Rating: " + property.rating.toString()

            Glide.with(view.context).load(property.image_url).centerCrop().into(productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }


}
