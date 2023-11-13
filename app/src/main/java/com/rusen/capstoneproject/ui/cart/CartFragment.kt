package com.rusen.capstoneproject.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val viewModel: CartViewModel by viewModels()

    private val cartAdapter = CartAdapter(
        onProductClick = ::onProductClick,
        onProductDelete = ::onProductDelete
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = cartAdapter
        binding.ivClearCart.setOnClickListener {
            viewModel.clearCart()
        }
        binding.btnPurchase.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToPaymentFragment()
            it.findNavController().navigate(action)
        }
        viewModel.showCartProductsEvent.observe(viewLifecycleOwner) {
            onViewStateChange(it)
        }
    }
    private fun onProductClick(productId: Long) {
        findNavController().navigate(
            CartFragmentDirections.actionCartFragmentToDetailFragment(
                productId = productId
            )
        )
    }

    private fun onViewStateChange(resource: Resource<List<Product>>) {
        when (resource) {
            is Resource.Error -> showMessage(resource.throwable.message)
            Resource.Loading -> Unit
            is Resource.Success -> updateUI(resource.data)
        }
    }

    private fun updateUI(products: List<Product>) {
        cartAdapter.submitList(products)

        // products icerisindeki urunlerinin fiyatinin toplamini bulucaksin. map kullanabilirsin. Eger indirimliyse indirimli fiyatini baz alacaksin
        val totalPrice = products.mapNotNull {
            if (it.saleState == true) {
                it.salePrice
            } else {
                it.price
            }
        }.sum()

        binding.tvTotalPrice.text = String.format(
            binding.tvTotalPrice.context.getString(R.string.product_price),
            totalPrice
        )

        if (totalPrice == 0.0) {
            binding.btnPurchase.visibility = View.GONE
            binding.tvTotal.visibility = View.GONE
            binding.tvTotalPrice.visibility = View.GONE
            binding.ivClearCart.visibility = View.GONE
        }
    }
    private fun onProductDelete(productId: Long) {
        viewModel.onProductDelete(productId, cartAdapter.currentList.toMutableList())
    }
}