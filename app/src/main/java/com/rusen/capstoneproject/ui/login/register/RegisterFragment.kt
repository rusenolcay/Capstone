package com.rusen.capstoneproject.ui.login.register

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.BaseFragment
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.btnCreate.setOnClickListener {
            if (binding.email.text.toString().isBlank()) {
                showMessage(getString(R.string.email_address))
            } else if (binding.password.text.toString().length < 6) {
                showMessage(getString(R.string.password_message))
            } else {
                register()
            }
        }
    }

    private fun register() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            binding.email.text.toString(),
            binding.password.text.toString()
        ).addOnSuccessListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
        }.addOnFailureListener {
            showMessage(getString(R.string.try_again))
        }
    }
}
