package com.app.smartdoor

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.R
import com.app.iot.databinding.ActivityMainBinding
import com.app.iot.ui.visitor.LoginActivity
import com.app.iot.ui.visitor.ProfileActivity
import com.app.iot.ui.user.UserListActivity
import com.app.iot.ui.visitor.HistoryActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity1 : AppCompatActivity() {

    // Write a message to the database

    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database : DatabaseReference
    private val senderID = "0F:36:45:55:5C:26:12:8A:2F:6C:B1:04:8D:03:F5:D8:18:3A:40:28"

    private var admin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // getToken()
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        val builder = AlertDialog.Builder(this)

        builder.setTitle(R.string.logout_dialog)
        builder.setMessage(R.string.logout_msg)
        binding.logoutBtn.setOnClickListener {
            builder.setPositiveButton("Yes") { dialogInterface, which ->

                firebaseAuth.signOut()
                checkUser()
            }
            builder.setNegativeButton("Cancel") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

        binding.profile.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        binding.htryBtn.setOnClickListener{
            if(admin) {
                startActivity(Intent(this, HistoryActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(applicationContext, "This feature is available to Admin only", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button3.setOnClickListener{
            if(admin) {
                startActivity(Intent(applicationContext, UserListActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "This feature is available to Admin only", Toast.LENGTH_LONG).show()
            }
        }

    }

//
//    @SuppressLint("StringFormatInvalid")
//    private fun getToken() {
//
//        Thread(Runnable {
//            try {
//                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//                    if (!task.isSuccessful) {
//                        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                        return@OnCompleteListener
//                    }
//
//                    // Get new FCM registration token
//                    val token = task.result
//
//                    // Log and toast
//                    val msg = getString(R.string.msg_token_fmt, token)
//                    Log.d(TAG, msg)
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                })
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }).start()
//    }
//    private fun onNewToken(token: String) {
//        Log.d(TAG, "Refreshed token: $token")
//
//
//    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        database = FirebaseDatabase.getInstance().getReference("Users")
        if(firebaseUser != null){
            val email = firebaseUser.email

            if (email != null) {
                val emailwithcomma = email.replace('.', ',')
                database.child(emailwithcomma).get().addOnSuccessListener {
                    if (it.exists()){
                        val fullname = it.child("fullname").value
                        val role = it.child("role").value
                        if (role == "admin")
                            admin = true
                        //  binding.userNameText.text = email
                        binding.text1.text="Welcome , " +fullname
                    }
                    else{
                        Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

            }
        }
        else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
