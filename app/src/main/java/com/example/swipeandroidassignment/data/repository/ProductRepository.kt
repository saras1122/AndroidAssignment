package com.example.swipeandroidassignment.data.repository

import com.example.swipeandroidassignment.data.api.ProductApiService
import com.example.swipeandroidassignment.data.models.Product
import com.example.swipeandroidassignment.utils.UiState
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProductRepository(private val apiService: ProductApiService) {

    suspend fun fetchProducts(): List<Product> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun uploadImage(image: MultipartBody.Part): Nothing? {
        return null

    }

    suspend fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        image: MultipartBody.Part?
    ): UiState<Boolean> {
        val productNameBody = productName.toRequestBody(MultipartBody.FORM)
        val productTypeBody = productType.toRequestBody(MultipartBody.FORM)
        val priceBody = price.toRequestBody(MultipartBody.FORM)
        val taxBody = tax.toRequestBody(MultipartBody.FORM)



        return try {
            val imageUrl = if (image != null) {
                uploadImage(image)
            } else {
                null
            }

            val response =
                apiService.addProduct(productNameBody, productTypeBody, priceBody, taxBody,imageUrl)

            if (response.isSuccessful) {
                UiState.Success(true)
            } else {
                UiState.Failure("Failed to add product")
            }
        } catch (e: Exception) {
            UiState.Failure(e.message ?: "Unknown error occurred")
        }
    }
}
