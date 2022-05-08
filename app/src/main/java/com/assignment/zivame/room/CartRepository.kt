package com.assignment.zivame.room

class CartRepository(private val db: CartDatabase) {

    suspend fun insert(item: CartItems) = db.getCartDao().insert(item)
    suspend fun delete(item: CartItems) = db.getCartDao().nukeTable(item)

    fun allGroceryItems() = db.getCartDao().getAllCartItems()
}