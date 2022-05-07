package com.assignment.zivame

import com.squareup.moshi.Json

data class ResponseData(
    @field:Json(name = "products") val products: List<ProductProperty>
    )

data class ProductProperty(
    @field:Json(name = "name")val name: String? = null,
    @field:Json(name = "price")val price: String? = null ,
    @field:Json(name = "image_url")val image_url: String? = null,
    @field:Json(name = "rating")val rating: String? = null
    )