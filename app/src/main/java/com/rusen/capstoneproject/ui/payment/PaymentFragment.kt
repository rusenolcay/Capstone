package com.rusen.capstoneproject.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentPaymentBinding
import com.rusen.capstoneproject.ui.ViewModelFactory

class PaymentFragment : BaseFragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private val viewModel: PaymentViewModel by viewModels { ViewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnForward.setOnClickListener {
            viewModel.attemptPayment(
                date = binding.tvDate.text.toString(),
                cartNumber = binding.tvCartNumber.text.toString(),
                name = binding.tvName.text.toString()
            )
        }
        viewModel.showMessageEvent.observe(viewLifecycleOwner) {
            showMessage(it)
        }
        viewModel.navigateSuccessEvent.observe(viewLifecycleOwner) {
            navigateSuccess()
        }
    }

    private fun navigateSuccess() {
        val action = PaymentFragmentDirections.actionPaymentFragmentToSuccessFragment()
        findNavController().navigate(action)
    }
}