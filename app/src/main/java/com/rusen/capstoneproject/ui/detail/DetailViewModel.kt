package com.rusen.capstoneproject.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.AddToCardRequest
import com.rusen.capstoneproject.data.model.AddToCardResponse
import com.rusen.capstoneproject.data.model.GetProductDetailResponse
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.ui.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : BaseViewModel() {

    private val showProductDetail = MutableLiveData<Product>()
    val showProductDetailEvent: LiveData<Product> = showProductDetail

    fun getProductDetail(id: Long) {
        CapstoneApplication.productService?.getProductDetail(id)
            ?.enqueue(object : Callback<GetProductDetailResponse> {
                override fun onResponse(
                    call: Call<GetProductDetailResponse>,
                    response: Response<GetProductDetailResponse>
                ) {
                    val result = response.body()
                    result?.let {
                        if (result.status == 200) {
                            result.product?.let { product ->
                                showProductDetail.value = product
                            }
                        } else {
                            showMessage.value = result.message
                        }
                    }
                }

                override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                    Log.e("GetProductDetail", t.message.orEmpty())
                }
            })
    }

    fun addProductToCart(productId: Long) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            val request = AddToCardRequest(userId = user.uid, productId = productId)
            CapstoneApplication.productService?.addProductToCart(request)
                ?.enqueue(object : Callback<AddToCardResponse> {
                    override fun onResponse(
                        call: Call<AddToCardResponse>,
                        response: Response<AddToCardResponse>
                    ) {
                        val result = response.body()
                        showMessage.value = result?.message
                    }

                    override fun onFailure(call: Call<AddToCardResponse>, t: Throwable) {
                        Log.e("GetProductDetail", t.message.orEmpty())
                    }
                })
        }
    }
}