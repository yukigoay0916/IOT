package com.app.iot.ui.visitor

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityEditProfileBinding
import com.app.iot.R
import com.app.iot.ui.visitor.LoginActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database : DatabaseReference

    var selected : Uri? = null

    private var username = ""
    private var fullname = ""
    private var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.title = "Edit Profile"

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        val user = firebaseAuth.currentUser
        if(user != null){
            val email = user.email
            if (email != null) {
                val emailwithcomma = email.replace('.', ',')
                database.child(emailwithcomma).get().addOnSuccessListener {
                    if (it.exists()){
                        val avt_url = it.child("avatar").value as String
                        val uname = it.child("username").value as String
                        val fname = it.child("fullname").value as String

                        binding.username.setText(uname)
                        binding.fullname.setText(fname)
                        Picasso.get().load(avt_url).into(binding.imageView)
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
        val builder = AlertDialog.Builder(this)

        builder.setTitle(R.string.logout_dialog)
        builder.setMessage(R.string.logout_msg)

        binding.BackBtn.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
        binding.savechangeBtn.setOnClickListener{
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                validateData()
            }
            builder.setNegativeButton("Cancel") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
        binding.uploadTv.setOnClickListener{
            uploadImage()
        }
    }

    private fun uploadImage() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 111)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){
            var bmp = data?.extras?.get("data") as Bitmap
            val imageViewPhoto : ImageView = binding.imageView
            imageViewPhoto.setImageBitmap(bmp)
        }
        if(requestCode == 111 && resultCode == Activity.RESULT_OK && data != null){
            selected = data.data
            progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Uploading image")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val filename = formatter.format(now)
            val storageReference = FirebaseStorage.getInstance().getReference("avatar/$filename")
            storageReference.putFile(selected!!)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    firebaseAuth = FirebaseAuth.getInstance()
                    database = FirebaseDatabase.getInstance().getReference("Users")
                    val fb_user = firebaseAuth.currentUser
                    val email = fb_user?.email
                    if (email != null) {
                        val emailwithcomma = email.replace('.', ',')
                        storageReference.downloadUrl.addOnSuccessListener {
                            database.child(emailwithcomma).child("avatar").setValue(it.toString())
                            startActivity(Intent(this, ProfileActivity::class.java))
                        }
                    }
                }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
        super.onBackPressed()
    }

    private fun validateData(){
        username = binding.username.text!!.toString().trim()
        password = binding.pass.editText!!.text.toString().trim()
        fullname = binding.fullname!!.text.toString().trim()
        database = FirebaseDatabase.getInstance().getReference("Users")
        val fb_user = firebaseAuth.currentUser
        if(fb_user != null) {
            val email = fb_user.email
            val emailwithcomma = email?.replace('.', ',')
            if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(
                    fullname
                )
            ) {
                Toast.makeText(this, "Nothing changes", Toast.LENGTH_SHORT).show()
            } else if (!TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(
                    fullname
                )
            ) {
                if (emailwithcomma != null) {
                    database.child(emailwithcomma).child("username").setValue(username).addOnSuccessListener {
                        Toast.makeText(this, "Username changes", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && TextUtils.isEmpty(
                    fullname
                )
            ) {
                Toast.makeText(this, "Password changes", Toast.LENGTH_SHORT).show()
                if (password.length < 6) {
                    binding.pass.error = "Password must at least 6 characters long"
                } else {
                    database.child(emailwithcomma!!).child("password").setValue(password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            println("Update Success")
                        } else {
                            println("Error Update")
                        }
                    }
                }
            } else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && !TextUtils.isEmpty(
                    fullname
                )
            ) {
                if (emailwithcomma != null) {
                    database.child(emailwithcomma).child("fullname").setValue(fullname).addOnSuccessListener {
                        Toast.makeText(this, "Fullname changes", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && TextUtils.isEmpty(
                    fullname
                )
            ) {
                if (password.length < 6) {
                    binding.pass.error = "Password must at least 6 characters long"
                }
                else{
                    if (emailwithcomma != null) {
                        database.child(emailwithcomma).child("username").setValue(username).addOnSuccessListener {
                            Toast.makeText(this, "Username and password changes", Toast.LENGTH_SHORT).show()
                        }
                        database.child(emailwithcomma!!).child("password").setValue(password).addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                println("Update Success")

                            } else {
                                println("Error Update")
                            }
                        }
                    }
                }
            } else if (TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(
                    fullname
                )
            ) {
                if (password.length < 6) {
                    binding.pass.error = "Password must at least 6 characters long"
                }
                else{
                    if (emailwithcomma != null) {
                        database.child(emailwithcomma).child("fullname").setValue(fullname).addOnSuccessListener {
                            Toast.makeText(this, "Password and fullname changes", Toast.LENGTH_SHORT).show()
                        }
                        database.child(emailwithcomma!!).child("password").setValue(password).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                println("Update Success")

                            } else {
                                println("Error Update")
                            }
                        }
                    }
                }
            } else if (!TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && !TextUtils.isEmpty(
                    fullname
                )
            ) {
                if (emailwithcomma != null) {
                    database.child(emailwithcomma).child("fullname").setValue(fullname).addOnSuccessListener {
                        Toast.makeText(this, "Username and fullname changes", Toast.LENGTH_SHORT).show()
                    }
                    database.child(emailwithcomma).child("username").setValue(username).addOnSuccessListener {
                    }
                }
            } else if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(
                    fullname
                )
            ) {
                if (password.length < 6) {
                    binding.pass.error = "Password must at least 6 characters long"
                }
                else{
                    if (emailwithcomma != null) {
                        database.child(emailwithcomma).child("fullname").setValue(fullname).addOnSuccessListener {
                            Toast.makeText(this, "All changes", Toast.LENGTH_SHORT).show()
                        }
                        database.child(emailwithcomma).child("username").setValue(username).addOnSuccessListener {
                        }
                        database.child(emailwithcomma!!).child("password").setValue(password).addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                println("Update Success")
                            } else {
                                println("Error Update")
                            }
                        }
                    }
                }
            }
        }
        startActivity(Intent(this, ProfileActivity::class.java))
    }
}