package com.rusen.capstoneproject.ui.success

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.ClearCartRequest
import com.rusen.capstoneproject.data.model.GetClearCartResponse
import com.rusen.capstoneproject.databinding.FragmentSuccessBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding by viewBinding(FragmentSuccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clearCart()
        binding.btnGoHome.setOnClickListener {
            val action = SuccessFragmentDirections.actionSuccessFragmentToHomeFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun clearCart() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            CapstoneApplication.productService?.clearCart(ClearCartRequest(userId = user.uid))
                ?.enqueue(object : Callback<GetClearCartResponse> {
                    override fun onResponse(
                        call: Call<GetClearCartResponse>,
                        response: Response<GetClearCartResponse>
                    ) {
                        val result = response.body()
                        if (result?.status != 200) {
                            Toast.makeText(context, result?.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<GetClearCartResponse>, t: Throwable) {
                        Log.e("GetClear", t.message.orEmpty())
                    }
                })
        }
    }
}