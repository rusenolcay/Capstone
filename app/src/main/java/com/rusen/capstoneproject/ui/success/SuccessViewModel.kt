package com.rusen.capstoneproject.ui.success

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.ClearCartRequest
import com.rusen.capstoneproject.data.model.GetClearCartResponse
import com.rusen.capstoneproject.ui.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuccessViewModel : BaseViewModel() {

    fun clearCart() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            CapstoneApplication.productService?.clearCart(ClearCartRequest(userId = user.uid))
                ?.enqueue(object : Callback<GetClearCartResponse> {
                    override fun onResponse(
                        call: Call<GetClearCartResponse>,
                        response: Response<GetClearCartResponse>
                    ) {
                        val result = response.body()
                        if (result?.status != 200) {
                            showMessage.value = result?.message
                        }
                    }

                    override fun onFailure(call: Call<GetClearCartResponse>, t: Throwable) {
                        Log.e("GetClear", t.message.orEmpty())
                    }
                })
        }
    }
}