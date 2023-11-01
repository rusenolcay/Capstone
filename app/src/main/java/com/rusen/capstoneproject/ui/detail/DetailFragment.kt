package com.rusen.capstoneproject.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.GetProductDetailResponse
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.databinding.FragmentDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()

    private val imageAdapter = ImageAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.btnAddToBasket.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToCartFragment()
            it.findNavController().navigate(action)
        }

        getProductDetail(args.productId)
        binding.rvImageList.adapter = imageAdapter
    }

    private fun getProductDetail(id: Long) {
        CapstoneApplication.productService?.getProductDetail(id)
            ?.enqueue(object : Callback<GetProductDetailResponse> {
                override fun onResponse(
                    call: Call<GetProductDetailResponse>,
                    response: Response<GetProductDetailResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200 && result.product != null) {
                        onRetrievedResponse(result.product)
                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                    Log.e("GetProductDetail", t.message.orEmpty())
                }
            })
    }

    private fun onRetrievedResponse(product: Product) {
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

            updateImageList(product)
        }
    }

    private fun updateImageList(product: Product) {
        val imageList = with(product) {
            listOf(imageOne, imageTwo, imageThree)
        }
        imageAdapter.submitList(imageList)
    }
}