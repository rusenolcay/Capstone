package com.rusen.capstoneproject.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rusen.capstoneproject.databinding.ItemImageBinding

class ImageAdapter :
    ListAdapter<String, ImageAdapter.ImageViewHolder>(ImageViewHolder.ImageDiffUtilCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(image = getItem(position))

    class ImageViewHolder(
        private val binding: ItemImageBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: String?) {
            Glide.with(binding.root.context).load(image).into(binding.ivImage)
        }

        class ImageDiffUtilCallBack : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
