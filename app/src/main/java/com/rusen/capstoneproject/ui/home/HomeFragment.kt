package com.rusen.capstoneproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.GetProductsResponse
import com.rusen.capstoneproject.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val discountedProductsAdapter = ProductsAdapter(onProductClick = ::onProductClick)
    private val productsAdapter = ProductsAdapter(onProductClick = ::onProductClick)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProducts()

        with(binding) {
            rvShopList.adapter = productsAdapter
            rvShopListSale.adapter = discountedProductsAdapter
        }
    }

    private fun getProducts() {
        CapstoneApplication.productService?.getProducts()?.enqueue(object :
            Callback<GetProductsResponse> {
            override fun onResponse(
                call: Call<GetProductsResponse>,
                response: Response<GetProductsResponse>
            ) {
                val result = response.body()
                if (result?.status == 200) {
                    updateUI(result)
                } else {
                    Toast.makeText(context, result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                Log.e("GetProducts", t.message.orEmpty())
            }
        })
    }

    fun onProductClick(id: Long){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }

    /**
     * Servisten gelen result'u kullanarak tum urunleri submitList yardimiyla recyclerview adapter'umuze ekledik.
     */
    private fun updateUI(result: GetProductsResponse) {
        productsAdapter.submitList(result.products)

        // result.products icindeki saleState degeri true olan urunleri filtreleyerek bulduk.
        val discountedProducts = result.products?.filter { it.saleState == true }
        discountedProductsAdapter.submitList(discountedProducts)
    }
}