package com.rusen.capstoneproject.ui.login.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.common.viewBinding
import com.rusen.capstoneproject.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.btnCreate.setOnClickListener {
            if (binding.email.text.toString().isBlank()) {
                showMessage("Please enter a valid email address!")
            } else if (binding.password.text.toString().length < 6) {
                showMessage("Please enter a password of at least 6 digits!")
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
            showMessage("Successful")
        }.addOnFailureListener {
            showMessage("Unsuccessful")
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
