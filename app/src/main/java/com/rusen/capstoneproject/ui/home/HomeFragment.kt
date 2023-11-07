package com.rusen.capstoneproject.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.databinding.FragmentHomeBinding
import com.rusen.capstoneproject.ui.ViewModelFactory

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory }

    private val discountedProductsAdapter = ProductsAdapter(onProductClick = ::onProductClick)
    private val productsAdapter = ProductsAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        with(binding) {
            rvShopList.adapter = productsAdapter
            rvShopListSale.adapter = discountedProductsAdapter
        }
        viewModel.showMessageEvent.observe(viewLifecycleOwner) { message ->
            showMessage(message)
        }
        viewModel.updateUIEvent.observe(viewLifecycleOwner){
            updateUI(it)
        }
    }

    private fun onProductClick(id: Long) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }

    private fun updateUI(products: List<Product>?) {
        productsAdapter.submitList(products)

        // result.products icindeki saleState degeri true olan urunleri filtreleyerek bulduk.
        val discountedProducts = products?.filter { it.saleState == true }
        discountedProductsAdapter.submitList(discountedProducts)
    }
}