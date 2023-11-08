package com.rusen.capstoneproject.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.ClearCartRequest
import com.rusen.capstoneproject.data.model.GetClearCartResponse
import com.rusen.capstoneproject.data.model.GetDeleteFromCartRequest
import com.rusen.capstoneproject.data.model.GetDeleteFromCartResponse
import com.rusen.capstoneproject.data.model.GetProductsCartResponse
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.ui.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : BaseViewModel() {

    private val showCartProducts = MutableLiveData<List<Product>>()
    val showCartProductsEvent: LiveData<List<Product>> = showCartProducts


    fun getCartProducts() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            CapstoneApplication.productService?.getProductsCart(userId = user.uid)?.enqueue(object :
                Callback<GetProductsCartResponse> {
                override fun onResponse(
                    call: Call<GetProductsCartResponse>,
                    response: Response<GetProductsCartResponse>
                ) {
                    val result = response.body()
                    if (result?.status == 200) {
                        showCartProducts.value = result.products
                    } else {
                        showCartProducts.value = emptyList()
                        showMessage.value = result?.message
                    }
                }

                override fun onFailure(call: Call<GetProductsCartResponse>, t: Throwable) {
                    Log.e("GetProducts", t.message.orEmpty())
                }
            })
        }
    }

    fun clearCart() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            CapstoneApplication.productService?.clearCart(ClearCartRequest(userId = user.uid))
                ?.enqueue(object : Callback<GetClearCartResponse> {
                    override fun onResponse(
                        call: Call<GetClearCartResponse>,
                        response: Response<GetClearCartResponse>
                    ) {
                        val result = response.body()
                        if (result?.status == 200) {
                            showCartProducts.value = emptyList()
                        } else {
                            showMessage.value = result?.message
                        }
                    }

                    override fun onFailure(call: Call<GetClearCartResponse>, t: Throwable) {
                        Log.e("GetClear", t.message.orEmpty())
                    }
                })
        }
    }

    fun onProductDelete(productId: Long, currentList: MutableList<Product>) {
        CapstoneApplication.productService?.deleteCart(GetDeleteFromCartRequest(productId))
            ?.enqueue(object : Callback<GetDeleteFromCartResponse> {
                override fun onResponse(
                    call: Call<GetDeleteFromCartResponse>,
                    response: Response<GetDeleteFromCartResponse>
                ) {
                    val result = response.body()
                    if (result?.status == 200) {
                        //Basarili istek sonucunda silinmesi istenilen urunun productId'sini suanki listede filtreler ve bulunca siler.
                        currentList.removeIf { it.id == productId }
                        showCartProducts.value = currentList
                    } else {
                        showMessage.value = result?.message
                    }
                }

                override fun onFailure(call: Call<GetDeleteFromCartResponse>, t: Throwable) {
                    Log.e("GetClear", t.message.orEmpty())
                }
            })
    }
}