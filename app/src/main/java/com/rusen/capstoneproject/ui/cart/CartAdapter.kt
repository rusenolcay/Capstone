package com.rusen.capstoneproject.ui.cart

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
import com.rusen.capstoneproject.databinding.ItemCartBinding


class CartAdapter(
    private val onProductClick: (Long) -> Unit

) : ListAdapter<Product, CartAdapter.CartViewHolder>(CartDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, onProductClick)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val onProductClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                tvTitleCart.text = product.title
                tvPrice.text = String.format(
                   tvPrice.context.getString(R.string.product_price),
                    product.price
                )
                tvDiscounted.text = String.format(
                    tvDiscounted.context.getString(R.string.product_price),
                    product.salePrice
                )
                if (product.saleState == true) {
                    tvDiscounted.visibility = View.VISIBLE
                   tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                  tvDiscounted.visibility = View.GONE
                }
                Glide.with(binding.root.context).load(product.imageOne).into(binding.ivCartImage)
                root.setOnClickListener {
                    product.id?.let { onProductClick(it) }
                }
            }
        }
    }

    class CartDiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}