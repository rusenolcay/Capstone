package com.rusen.capstoneproject.data.source.remote

import android.util.Log
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.AddToCardRequest
import com.rusen.capstoneproject.data.model.AddToCardResponse
import com.rusen.capstoneproject.data.model.ClearCartRequest
import com.rusen.capstoneproject.data.model.GetClearCartResponse
import com.rusen.capstoneproject.data.model.GetDeleteFromCartRequest
import com.rusen.capstoneproject.data.model.GetDeleteFromCartResponse
import com.rusen.capstoneproject.data.model.GetProductsCartResponse
import com.rusen.capstoneproject.data.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRemoteDataSource {

    private val service = CapstoneApplication.productService

    fun getCartProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: (String?) -> Unit,
        userId: String
    ) {
        service?.getProductsCart(userId)?.enqueue(object : Callback<GetProductsCartResponse> {
            override fun onResponse(
                call: Call<GetProductsCartResponse>,
                response: Response<GetProductsCartResponse>
            ) {
                val result = response.body()
                if (result?.status == 200) {
                    onSuccess(result.products)
                } else {
                    onFailure(result?.message)
                }
            }

            override fun onFailure(call: Call<GetProductsCartResponse>, t: Throwable) {
                Log.e("GetProducts", t.message.orEmpty())
            }
        })
    }

    fun clearCart(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        userId: String
    ) {
        val request = ClearCartRequest(userId)
        service?.clearCart(request)?.enqueue(object : Callback<GetClearCartResponse> {
            override fun onResponse(
                call: Call<GetClearCartResponse>,
                response: Response<GetClearCartResponse>
            ) {
                val result = response.body()
                if (result?.status == 200) {
                    onSuccess()
                } else {
                    onFailure(result?.message)
                }
            }

            override fun onFailure(call: Call<GetClearCartResponse>, t: Throwable) {
                Log.e("GetClear", t.message.orEmpty())
            }
        })
    }

    fun deleteProductFromCart(
        onSuccess: (List<Product>) -> Unit,
        onFailure: (String?) -> Unit,
        userId: String,
        productId: Long,
        currentList: MutableList<Product>
    ) {
        val request = GetDeleteFromCartRequest(productId, userId)
        val deleteCart = service?.deleteCart(request)
        deleteCart?.enqueue(object : Callback<GetDeleteFromCartResponse> {
            override fun onResponse(
                call: Call<GetDeleteFromCartResponse>,
                response: Response<GetDeleteFromCartResponse>
            ) {
                val result = response.body()
                if (result?.status == 200) {
                    //Basarili istek sonucunda silinmesi istenilen urunun productId'sini suanki listede filtreler ve bulunca siler.
                    currentList.removeIf { it.id == productId }
                    onSuccess(currentList)
                } else {
                    onFailure(result?.message)
                }
            }

            override fun onFailure(call: Call<GetDeleteFromCartResponse>, t: Throwable) {
                Log.e("GetClear", t.message.orEmpty())
            }
        })
    }

    fun addProductToCart(
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit,
        productId: Long,
        userId: String
    ) {
        val request = AddToCardRequest(userId = userId, productId = productId)
        service?.addProductToCart(request)?.enqueue(object : Callback<AddToCardResponse> {
            override fun onResponse(
                call: Call<AddToCardResponse>,
                response: Response<AddToCardResponse>
            ) {
                val result = response.body()
                if (result?.status == 200) {
                    onSuccess(result.message)
                } else {
                    onFailure(result?.message)
                }
            }

            override fun onFailure(call: Call<AddToCardResponse>, t: Throwable) {
                Log.e("GetProductDetail", t.message.orEmpty())
            }
        })
    }
}