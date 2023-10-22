package com.rusen.capstoneproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.MainActivity
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val DELAY = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    /**
     * Main Looper kullanarak 3000 ms' lik cekme yaratiyoruz.
     */
    private fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            startNextScreen()
            finish()
        }, DELAY)
    }

    /**
     * Eger current user varsa MainActicity'e yoksa LoginActivity'e yonlendirir.
     */
    private fun startNextScreen() {
        FirebaseAuth.getInstance().currentUser?.let {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } ?: run {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}