package com.rusen.capstoneproject.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentFavoritesBinding
import com.rusen.capstoneproject.ui.ViewModelFactory
import com.rusen.capstoneproject.ui.cart.CartFragmentDirections

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {
    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel: FavoritesViewModel by viewModels { ViewModelFactory }

    private val favoritesAdapter = FavoritesAdapter(
        onProductClick = ::onProductClick,
        onProductDelete = ::onProductDelete
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = favoritesAdapter

        binding.ivClearFavorite.setOnClickListener {
            viewModel.clearFavorites()
        }

        viewModel.showFavoriteProductsEvent.observe(viewLifecycleOwner){
            favoritesAdapter.submitList(it)
        }
        viewModel.showMessageEvent.observe(viewLifecycleOwner){
           showMessage(it)
        }
        viewModel.getFavoriteProducts()
    }

    private fun onProductClick(productId: Long) {
        findNavController().navigate(
            CartFragmentDirections.actionCartFragmentToDetailFragment(
                productId = productId
            )
        )
    }

    private fun onProductDelete(productId: Long) {
        viewModel.onProductDelete(productId, favoritesAdapter.currentList.toMutableList())
    }
}