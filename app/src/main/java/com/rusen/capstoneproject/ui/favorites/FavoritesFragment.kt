package com.rusen.capstoneproject.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {
    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel: FavoritesViewModel by viewModels()

    private val favoritesAdapter = FavoritesAdapter(
        onProductClick = ::onProductClick,
        onProductDelete = ::onProductDelete
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = favoritesAdapter

        binding.tvClearFavorite.setOnClickListener {
            viewModel.clearFavorites()
            binding.tvClearFavorite.visibility = View.GONE
        }

        viewModel.showFavoriteProductsEvent.observe(viewLifecycleOwner) {
            binding.tvClearFavorite.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
            favoritesAdapter.submitList(it)
        }
        viewModel.showMessageEvent.observe(viewLifecycleOwner) {
            showMessage(it)
        }
        viewModel.getFavoriteProducts()
    }

    private fun onProductClick(productId: Long) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(
                productId = productId
            )
        )
    }

    private fun onProductDelete(productId: Long) {
        viewModel.onProductDelete(productId, favoritesAdapter.currentList.toMutableList())
    }
}