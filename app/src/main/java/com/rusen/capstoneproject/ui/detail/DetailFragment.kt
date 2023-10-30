package com.rusen.capstoneproject.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.GetProductDetailResponse
import com.rusen.capstoneproject.databinding.FragmentDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()

    val imageAdapter = ImageAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                        result.product.apply {
                            binding.tvTitle.text = title
                            binding.tvPrice.text = String.format(
                                binding.tvPrice.context.getString(R.string.product_price), price
                            )
                            binding.tvDiscounted.text = String.format(
                                binding.tvDiscounted.context.getString(R.string.product_price),
                                salePrice
                            )
                            if (saleState == true) {
                                binding.tvDiscounted.visibility = View.VISIBLE
                                binding.tvPrice.paintFlags =
                                    binding.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            } else {
                                binding.tvDiscounted.visibility = View.GONE

                            }
                            binding.tvDesriction.text = description

                            val imageList = listOf(imageOne, imageTwo, imageThree)
                            imageAdapter.submitList(imageList)
                        }
                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                    Log.e("GetProductDetail", t.message.orEmpty())
                }

            })
    }
}