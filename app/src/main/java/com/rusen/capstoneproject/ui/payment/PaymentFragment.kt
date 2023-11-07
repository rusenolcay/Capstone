package com.rusen.capstoneproject.ui.payment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentPaymentBinding

class PaymentFragment : BaseFragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnForward.setOnClickListener {
            if (binding.tvCartNumber.text.toString().length<16) {
                showMessage(getString(R.string.card_number_message))
            } else if (binding.tvDate.text.toString().length<5) {
                showMessage(getString(R.string.card_date_message))
            } else if (binding.tvName.text.toString().isBlank()) {
                showMessage(getString(R.string.card_name_message))
            } else {
                navigateSuccess()
            }
        }
    }

    private fun navigateSuccess() {
        val action = PaymentFragmentDirections.actionPaymentFragmentToSuccessFragment()
        findNavController().navigate(action)
    }
}