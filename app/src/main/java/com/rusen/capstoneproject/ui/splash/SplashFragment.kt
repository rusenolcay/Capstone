package com.rusen.capstoneproject.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    companion object {
        private const val DELAY = 3000L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /**
     * Main Looper kullanarak 3000 ms' lik cekme yaratiyoruz.
     */
    private fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            startNextScreen()
        }, DELAY)
    }

    /**
     * Eger current user varsa MainActicity'e yoksa LoginActivity'e yonlendirir.
     */
    private fun startNextScreen() {
        FirebaseAuth.getInstance().currentUser?.let {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        } ?: run {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
        }
    }
}