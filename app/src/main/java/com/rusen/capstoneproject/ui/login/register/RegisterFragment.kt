package com.rusen.capstoneproject.ui.login.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentRegisterBinding
import com.rusen.capstoneproject.ui.ViewModelFactory
class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    private val viewModel: RegisterViewModel by viewModels { ViewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.btnCreate.setOnClickListener {
            viewModel.attemptRegister(
                email = binding.email.text.toString(),
                password = binding.password.text.toString()
            )
        }
        viewModel.showResourceMessageEvent.observe(viewLifecycleOwner) { message ->
            showMessage(getString(message))
        }
        viewModel.navigateHomeEvent.observe(viewLifecycleOwner) {
            navigateHome()
        }
    }

    private fun navigateHome() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
    }
}
