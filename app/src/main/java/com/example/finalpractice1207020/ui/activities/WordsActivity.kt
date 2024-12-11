package com.example.finalpractice1207020.ui.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalpractice1207020.databinding.ActivityWordsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class WordsActivity : AppCompatActivity() {
    /** view binding */
    private lateinit var binding: ActivityWordsBinding
    /** Firebase auth object */
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate the view binding
        binding = ActivityWordsBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // get the Firebase auth object
        auth = Firebase.auth

        // add custom handling for the user pressing the device back button
        onBackPressedDispatcher.addCallback(this) {
            // show an alert
            AlertDialog.Builder(this@WordsActivity)
                .setTitle("Log Out?")
                .setMessage("Are you sure you wish to log out?")
                .setPositiveButton("Log Out") { _, _ ->
                    // sign out the user from Firebase auth and close the activity back to Login
                    auth.signOut()
                    finish()
                }
                // empty negative button function, since we want to do nothing
                .setNegativeButton("Cancel") { _,_ ->}
                .show()
        }
    }
}