package com.example.finalpractice1207020.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalpractice1207020.R
import com.example.finalpractice1207020.WordsApplication
import com.example.finalpractice1207020.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    /** view binding */
    private lateinit var binding: ActivityLoginBinding
    /** Firebase auth object */
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate the view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // get the Firebase auth object
        auth = Firebase.auth

        // use a "with" block to access binding attributes without explicitly saying "binding."
        with (binding) {
            // add listener for the login button
            btnLogin.setOnClickListener {
                // get email and password
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                var hasError = false

                // check for errors

                // no email given
                if (email.isBlank()) {
                    tvEmailError.text = getString(R.string.email_required)
                    hasError = true
                }
                // email isn't valid format
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tvEmailError.text = getString(R.string.email_valid_format)
                    hasError = true
                }

                // no password given
                if (password.isBlank()) {
                    tvPasswordError.text = getString(R.string.password_required)
                    hasError = true
                }

                // if any errors, just return
                if (hasError) {
                    return@setOnClickListener
                }

                // clear errors
                tvEmailError.text = ""
                tvPasswordError.text = ""

                // sign in with Firebase auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // log in succeeded, move to main activity
                            signedIn()
                        } else {
                            // log in failed, show toast
                            Toast.makeText(
                                baseContext,
                                R.string.invalid_credentials,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Firebase caches logged in user, so if currentUser exists then we are already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            signedIn()
        }
    }

    private fun signedIn() {
        // clear email and password on sign in
        binding.etEmail.setText("")
        binding.etPassword.setText("")

        // reload the displayed list of words now that the user is logged in
        (application as WordsApplication).repository.reloadUserWords()

        // start main activity
        val intent = Intent(this, WordsActivity::class.java)
        startActivity(intent)
    }
}