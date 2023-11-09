package com.rusen.capstoneproject.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.ui.ViewModelFactory

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels { ViewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateHomeEvent.observe(viewLifecycleOwner) {
            navigateHome()
        }
        viewModel.navigateLoginEvent.observe(viewLifecycleOwner) {
            navigateLogin()
        }
    }

    private fun navigateHome() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
    }

    private fun navigateLogin() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
    }
}