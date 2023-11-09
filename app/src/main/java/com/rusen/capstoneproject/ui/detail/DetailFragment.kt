package com.rusen.capstoneproject.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.databinding.FragmentDetailBinding
import com.rusen.capstoneproject.ui.ViewModelFactory

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel: DetailViewModel by viewModels { ViewModelFactory }

    private val args by navArgs<DetailFragmentArgs>()

    private val imageAdapter = ImageAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.btnAddToBasket.setOnClickListener {
            viewModel.addProductToCart(args.productId)
        }

        viewModel.getProductDetail(args.productId)
        binding.rvImageList.adapter = imageAdapter

        viewModel.showMessageEvent.observe(viewLifecycleOwner) {
            showMessage(it)
        }
        viewModel.showProductDetailEvent.observe(viewLifecycleOwner) {
            showProductDetail(it)
        }
        viewModel.changeFavoriteStatusEvent.observe(viewLifecycleOwner) {
            showFavoriteStatus(it)
        }
    }

    private fun showProductDetail(product: Product) {
        with(binding) {
            tvTitle.text = product.title
            tvPrice.text = String.format(getString(R.string.product_price), product.price)
            tvDescription.text = product.description
            if (product.saleState == true) {
                tvDiscounted.text =
                    String.format(getString(R.string.product_price), product.salePrice)
                tvDiscounted.visibility = View.VISIBLE
                tvPrice.paintFlags =
                    tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvDiscounted.visibility = View.GONE
            }
            tvCategory.text = String.format(getString(R.string.category), product.category)
            product.rate?.let { ratingBar.rating = it.toFloat() }

            binding.ivFavorite.setOnClickListener {
                viewModel.toggleFavoriteStatus(product)
            }

            updateImageList(product)
        }
    }

    private fun updateImageList(product: Product) {
        val imageList = with(product) {
            listOf(imageOne, imageTwo, imageThree)
        }
        imageAdapter.submitList(imageList)
    }

    private fun showFavoriteStatus(status: Boolean?) {
        if (status == true) {
            binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
        } else binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
    }
}