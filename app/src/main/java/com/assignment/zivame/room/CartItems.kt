package com.assignment.zivame.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItems(

    @ColumnInfo(name = "itemName")
    var itemName: String,

    @ColumnInfo(name = "itemHash")
    var itemHash : String,

    @ColumnInfo(name = "itemQuantity")
    var itemQuantity: Int,

    @ColumnInfo(name = "itemPrice")
    var itemPrice: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}