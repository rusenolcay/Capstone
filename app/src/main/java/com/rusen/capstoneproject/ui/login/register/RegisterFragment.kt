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
                Toast.makeText(
                    context,
                    "Lutfen gecerli bir email adresi giriniz!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.password.text.toString().length < 6) {
                Toast.makeText(
                    context,
                    "Lutfen en az 6 haneli bir sifre giriniz!",
                    Toast.LENGTH_SHORT
                ).show()
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
            Toast.makeText(context, "Başarılı", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Başarısız", Toast.LENGTH_SHORT).show()
        }
    }
}
