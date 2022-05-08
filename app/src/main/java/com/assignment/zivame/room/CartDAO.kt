package com.assignment.zivame.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItems)

    @Delete
    suspend fun nukeTable(item: CartItems)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItems>>
}