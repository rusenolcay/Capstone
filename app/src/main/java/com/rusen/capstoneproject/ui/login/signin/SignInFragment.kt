package com.rusen.capstoneproject.ui.login.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentSignInBinding
import com.rusen.capstoneproject.ui.ViewModelFactory

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels { ViewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }
        binding.btnLogin.setOnClickListener {
            viewModel.attemptSignIn(
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
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
    }
}