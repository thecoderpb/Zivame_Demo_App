package com.assignment.zivame.room

class CartRepository(private val db: CartDatabase) {

    suspend fun insert(item: CartItems) = db.getCartDao().insert(item)
    suspend fun delete(item: CartItems) = db.getCartDao().deleteItem(item)
    suspend fun nukeTable() = db.getCartDao().nukeTable()

    fun allCartItems() = db.getCartDao().getAllCartItems()
    fun allCartItemsCount() = db.getCartDao().getAllCartItemsCount()
    fun allCartItemsPrice() = db.getCartDao().getAllCartItemPrice()
    fun getProductQuantity(productName: String) = db.getCartDao().getProductQuantity(productName)
    fun incrementProductQuantity(itemQuantity: Int,productName: String)= db.getCartDao().incrementItemQuantity(itemQuantity,productName)
}