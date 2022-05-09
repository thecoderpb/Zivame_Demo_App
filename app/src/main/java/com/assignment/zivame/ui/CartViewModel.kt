package com.assignment.zivame.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.zivame.room.CartItems
import com.assignment.zivame.room.CartRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    fun insert(item: CartItems) = viewModelScope.launch {
        repository.insert(item)
    }

    fun delete(item: CartItems) = viewModelScope.launch {
        repository.delete(item)
    }

    fun nukeTable() = viewModelScope.launch {
        repository.nukeTable()
    }

    fun allCartItems() = repository.allCartItems()
    fun allCartItemsCount() = repository.allCartItemsCount()
    fun allCartItemsPrice() = repository.allCartItemsPrice()
    fun getItemQuantity(itemName: String) : LiveData<Int> = repository.getProductQuantity(itemName)
    fun incrementProductQuantity(itemQuantity: Int,productName: String) = repository.incrementProductQuantity(itemQuantity,productName)

}