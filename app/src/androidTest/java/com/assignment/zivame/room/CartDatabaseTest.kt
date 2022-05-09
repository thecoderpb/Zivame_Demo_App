package com.assignment.zivame.room

import android.app.Activity
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.assignment.zivame.MainActivity
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CartDatabaseTest : TestCase(){

    private lateinit var cartDAO: CartDAO
    private lateinit var db : CartDatabase
    private lateinit var context : Context

    @Before
    public override fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder<CartDatabase>(
            context, CartDatabase::class.java
        ).build()
        cartDAO = db.getCartDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadCartItems() = runBlocking {
        val cartItemsW = CartItems("One Plus","One Plus Hash",1,25000,"image_url")
        cartDAO.insert(cartItemsW)
        val cartItemsR = cartDAO.getCartItemsTest()
        assertThat(cartItemsW.itemName == (cartItemsR[0].itemName)).isTrue()
    }
}