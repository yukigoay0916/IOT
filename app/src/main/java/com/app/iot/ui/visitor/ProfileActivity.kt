package com.app.iot.ui.visitor

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.MainActivity
import com.app.iot.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Profile View"
//        var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("User")
//
//
//        reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //var i = 0
//                if(snapshot.exists()){
//                    //Toast.makeText(applicationContext,snapshot.toString(),Toast.LENGTH_SHORT).show()
//                    for (childSnapshot in snapshot.children) {
//                        var minutes = childSnapshot.key.toString()//uid
//                        //Toast.makeText(applicationContext,minutes,Toast.LENGTH_SHORT).show()
//
//                        binding.username.text = childSnapshot.child("name").getValue().toString()
//
//                    }
//                }else{
//                    Toast.makeText(applicationContext,"No Record",Toast.LENGTH_SHORT).show()
//                }
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//
//            }
//
//        })

        firebaseAuth = FirebaseAuth.getInstance()

        checkUser()

        binding.HomeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.editBtn.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
        super.onBackPressed()
    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        database = FirebaseDatabase.getInstance().getReference("Users")
        if (firebaseUser != null) {
            val email = firebaseUser.email
            if (email != null) {
                val emailwithcomma = email.replace('.', ',')
                database.child(emailwithcomma).get().addOnSuccessListener {
                    if (it.exists()) {
                        val avt_url = it.child("avatar").value as String
                     //   binding.emailTv.text = email
                        binding.username.text = it.child("username").value as String
                        binding.email.text = email
                        binding.fullname.text = it.child("fullname").value as String
                        val role = it.child("role").value as String
                        binding.role.text = role
                        //binding.admin.text = "hung2001"
                        Picasso.get().load(avt_url).into(binding.imageView)
                    } else {
                        Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}