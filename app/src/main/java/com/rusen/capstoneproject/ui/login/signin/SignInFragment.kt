package com.rusen.capstoneproject.ui.login.signin

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }
        binding.btnLogin.setOnClickListener {
            if (binding.email.text.toString().isBlank()) {
                showMessage(getString(R.string.email_address))
            } else if (binding.password.text.toString().length < 6) {
                showMessage(getString(R.string.password_message))
            } else {
                signIn()
            }
        }
    }

    private fun signIn() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            binding.email.text.toString(),
            binding.password.text.toString()
        ).addOnSuccessListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
        }.addOnFailureListener {
            showMessage(getString(R.string.try_again))
        }
    }
}