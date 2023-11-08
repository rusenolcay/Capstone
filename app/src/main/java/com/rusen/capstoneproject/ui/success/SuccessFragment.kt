package com.rusen.capstoneproject.ui.success

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentSuccessBinding
import com.rusen.capstoneproject.ui.ViewModelFactory

class SuccessFragment : BaseFragment(R.layout.fragment_success) {

    private val binding by viewBinding(FragmentSuccessBinding::bind)
    private val viewModel: SuccessViewModel by viewModels { ViewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clearCart()
        binding.btnGoHome.setOnClickListener {
            val action = SuccessFragmentDirections.actionSuccessFragmentToHomeFragment()
            it.findNavController().navigate(action)
        }
        viewModel.showMessageEvent.observe(viewLifecycleOwner){
            showMessage(it)
        }
    }
}