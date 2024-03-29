package com.rusen.capstoneproject.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.databinding.ItemProductBinding
import javax.inject.Inject


class ProductsAdapter @Inject constructor() :
    ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductDiffUtilCallBack()) {

    var onProductClick: ((Long) -> Unit)? = null
    var onChangedFavoriteStatus: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onProductClick, onChangedFavoriteStatus)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(product = getItem(position))

    inner class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onProductClick: ((Long) -> Unit)?,
        private val onChangedFavoriteStatus: ((Product) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                tvProductTitle.text = product.title
                tvProductPrice.text = String.format(
                    tvProductPrice.context.getString(R.string.product_price),
                    product.price
                )
                tvProductDiscountedPrice.text = String.format(
                    tvProductDiscountedPrice.context.getString(R.string.product_price),
                    product.salePrice
                )
                if (product.saleState == true) {
                    tvProductDiscountedPrice.visibility = View.VISIBLE
                    tvProductPrice.paintFlags =
                        tvProductPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvProductDiscountedPrice.visibility = View.GONE
                }
                setFavoriteIcon(product.favorite)
                Glide.with(root.context).load(product.imageOne).into(ivProductImage)
                ivFavorite.setOnClickListener {
                    onFavoriteToggleClicked(product)
                }
                root.setOnClickListener {
                    product.id?.let { onProductClick?.invoke(it) }
                }
            }
        }

        private fun onFavoriteToggleClicked(product: Product) {
            onChangedFavoriteStatus?.invoke(product)
            setFavoriteIcon(product.favorite)
        }

        private fun setFavoriteIcon(favorite: Boolean) {
            if (favorite) {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
}
