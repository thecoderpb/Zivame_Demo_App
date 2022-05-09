package com.assignment.zivame.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItems)

    @Delete
    suspend fun deleteItem(item: CartItems)

    @Query("DELETE FROM cart_items")
    suspend fun nukeTable()

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItems>>

    @Query("SELECT COUNT(*) FROM cart_items")
    fun getAllCartItemsCount(): LiveData<Int>

    @Query("SELECT SUM(itemPrice) FROM cart_items")
    fun getAllCartItemPrice(): LiveData<Int>

    @Query("SELECT itemQuantity FROM cart_items WHERE itemName=:productName")
    fun getProductQuantity(productName: String): LiveData<Int>

    @Query("UPDATE cart_items SET itemQuantity=:itemQuantity WHERE itemName=:productName")
    fun incrementItemQuantity(itemQuantity: Int, productName: String)
}