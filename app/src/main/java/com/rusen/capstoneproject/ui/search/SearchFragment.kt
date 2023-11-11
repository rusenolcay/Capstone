package com.rusen.capstoneproject.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.databinding.FragmentSearchBinding
import com.rusen.capstoneproject.ui.home.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels()
    private val productsAdapter = ProductsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsAdapter.onProductClick = ::onProductClick
        binding.rvProducts.adapter = productsAdapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.attemptSearch(newText)
                return true
            }
        })

        viewModel.showMessageEvent.observe(viewLifecycleOwner) {
            showMessage(it)
        }
        viewModel.showSearchProductsEvent.observe(viewLifecycleOwner) {
            onViewStateChange(it)
        }
    }

    private fun onProductClick(productId: Long) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                productId
            )
        )
    }

    private fun onViewStateChange(resource: Resource<List<Product>>) {
        when (resource) {
            is Resource.Error -> showMessage(resource.message)
            Resource.Loading -> Unit
            is Resource.Success -> showSearchedProducts(resource.data)
        }
    }


    private fun showSearchedProducts(products: List<Product>?) {
        productsAdapter.submitList(products)
    }
}