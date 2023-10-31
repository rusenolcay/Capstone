package com.rusen.capstoneproject.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.GetProductSearchResponse
import com.rusen.capstoneproject.databinding.FragmentSearchBinding
import com.rusen.capstoneproject.ui.home.ProductsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val productsAdapter = ProductsAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProducts.adapter = productsAdapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.length >= 3) {
                        getProductsByQuery(newText)
                    } else {
                        productsAdapter.submitList(emptyList())
                    }
                }
                return true
            }
        })
    }

    private fun getProductsByQuery(query: String) {
        CapstoneApplication.productService?.getProductsByQuery(query)
            ?.enqueue(object : Callback<GetProductSearchResponse> {
                override fun onResponse(
                    call: Call<GetProductSearchResponse>,
                    response: Response<GetProductSearchResponse>
                ) {
                    val productSearchResponse = response.body()
                    productsAdapter.submitList(productSearchResponse?.products)
                    if (productSearchResponse?.status != 200) {
                        Toast.makeText(
                            requireContext(),
                            productSearchResponse?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<GetProductSearchResponse>, t: Throwable) {
                    Log.e("GetProductSearchResponse", t.message.orEmpty())
                }
            })
    }

    private fun onProductClick(productId: Long) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                productId
            )
        )
    }
}