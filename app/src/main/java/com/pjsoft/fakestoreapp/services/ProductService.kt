package com.pjsoft.fakestoreapp.services

import com.pjsoft.fakestoreapp.models.Product
import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products")
   suspend fun getAllProducts() : List<Product>

   @GET("products/{id}")
    suspend fun getProductById(@Path("id") id:Int ) : Product
}
