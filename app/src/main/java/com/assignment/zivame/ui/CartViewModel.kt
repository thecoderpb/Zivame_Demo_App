package com.assignment.zivame.ui

import androidx.lifecycle.ViewModel
import com.assignment.zivame.room.CartItems
import com.assignment.zivame.room.CartRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    fun insert(item: CartItems) = GlobalScope.launch {
        repository.insert(item)
    }

    fun delete(item: CartItems) = GlobalScope.launch {
        repository.delete(item)
    }

    fun allGroceryItems() = repository.allGroceryItems()

}