package com.rusen.capstoneproject.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var discountedProductsAdapter: ProductsAdapter

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discountedProductsAdapter.onProductClick = ::onProductClick
        discountedProductsAdapter.onChangedFavoriteStatus = viewModel::onChangedFavoriteStatus
        productsAdapter.onProductClick = ::onProductClick
        productsAdapter.onChangedFavoriteStatus = viewModel::onChangedFavoriteStatus

        with(binding) {
            rvShopList.adapter = productsAdapter
            rvShopListSale.adapter = discountedProductsAdapter

            ivExitApp.setOnClickListener {
                showMessageDialog(
                    message = getString(R.string.exit_account_message),
                    positiveButtonAction = { _, _ -> navigateSignIn() })
            }
        }
        viewModel.showMessageEvent.observe(viewLifecycleOwner) { message ->
            showMessage(message)
        }
        viewModel.updateUIEvent.observe(viewLifecycleOwner) {
            onViewStateChange(it)
        }
    }

    private fun onProductClick(id: Long) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }

    private fun onViewStateChange(resource: Resource<List<Product>>) {
        binding.progressBar.visibility = View.GONE
        when (resource) {
            is Resource.Error -> showMessage(resource.throwable.message)
            Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
            is Resource.Success -> updateUI(resource.data)
        }
    }

    private fun updateUI(products: List<Product>) {
        productsAdapter.submitList(products)

        // result.products icindeki saleState degeri true olan urunleri filtreleyerek bulduk.
        val discountedProducts = products.filter { it.saleState == true }
        discountedProductsAdapter.submitList(discountedProducts)
    }

    private fun navigateSignIn() {
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSplashFragment())
    }
}