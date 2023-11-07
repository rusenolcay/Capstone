package com.rusen.capstoneproject.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.ClearCartRequest
import com.rusen.capstoneproject.data.model.GetClearCartResponse
import com.rusen.capstoneproject.data.model.GetDeleteFromCartRequest
import com.rusen.capstoneproject.data.model.GetDeleteFromCartResponse
import com.rusen.capstoneproject.data.model.GetProductsCartResponse
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.databinding.FragmentCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val cartAdapter = CartAdapter(
        onProductClick = ::onProductClick,
        onProductDelete = ::onProductDelete
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCartProducts()
        binding.recyclerView.adapter = cartAdapter
        binding.ivClearCart.setOnClickListener {
            clearCart()
        }
        binding.btnPurchase.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToPaymentFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun getCartProducts() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            CapstoneApplication.productService?.getProductsCart(userId = user.uid)?.enqueue(object :
                Callback<GetProductsCartResponse> {
                override fun onResponse(
                    call: Call<GetProductsCartResponse>,
                    response: Response<GetProductsCartResponse>
                ) {
                    val result = response.body()
                    if (result?.status == 200) {
                        updateUI(result.products)
                    } else {
                        updateUI(emptyList())
                        Toast.makeText(context, result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetProductsCartResponse>, t: Throwable) {
                    Log.e("GetProducts", t.message.orEmpty())
                }
            })
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
                        if (result?.status == 200) {
                            updateUI(emptyList())
                        } else {
                            Toast.makeText(context, result?.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<GetClearCartResponse>, t: Throwable) {
                        Log.e("GetClear", t.message.orEmpty())
                    }
                })
        }
    }

    private fun onProductClick(productId: Long) {
        findNavController().navigate(
            CartFragmentDirections.actionCartFragmentToDetailFragment(
                productId = productId
            )
        )
    }

    private fun updateUI(products: List<Product>) {
        cartAdapter.submitList(products)

        // products icerisindeki urunlerinin fiyatinin toplamini bulucaksin. map kullanabilirsin. Eger indirimliyse indirimli fiyatini baz alacaksin
        val totalPrice = products.mapNotNull {
            if (it.saleState == true) {
                it.salePrice
            } else {
                it.price
            }
        }.sum()

        binding.tvTotalPrice.text = String.format(
            binding.tvTotalPrice.context.getString(R.string.product_price),
            totalPrice
        )

        if (totalPrice == 0.0) {
            binding.btnPurchase.visibility = View.GONE
            binding.tvTotal.visibility = View.GONE
            binding.tvTotalPrice.visibility = View.GONE
            binding.ivClearCart.visibility = View.GONE
        }
    }

    private fun onProductDelete(productId: Long) {
        CapstoneApplication.productService?.deleteCart(GetDeleteFromCartRequest(productId))
            ?.enqueue(object : Callback<GetDeleteFromCartResponse> {
                override fun onResponse(
                    call: Call<GetDeleteFromCartResponse>,
                    response: Response<GetDeleteFromCartResponse>
                ) {
                    val result = response.body()
                    if (result?.status == 200) {
                        //Basarili istek sonucunda silinmesi istenilen urunun productId'sini suanki listede filtreler ve bulunca siler.
                        val productList = cartAdapter.currentList.toMutableList()
                        productList.removeIf { it.id == productId }
                        updateUI(productList)
                    } else {
                        Toast.makeText(context, result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetDeleteFromCartResponse>, t: Throwable) {
                    Log.e("GetClear", t.message.orEmpty())
                }
            })
    }
}