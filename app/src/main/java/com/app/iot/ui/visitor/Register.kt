package com.app.iot.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityRegisterBinding
import com.app.iot.ui.user.User
import com.app.iot.ui.visitor.LoginActivity
import com.app.iot.ui.visitor.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database : DatabaseReference

    private var fullname = ""
    private var username = ""
    private var email = ""
    private var password = ""
    private var password2 = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

//        binding.HaveAccountTv.setOnClickListener{
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

        binding.textView2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registerBtn.setOnClickListener{
            validateData()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun validateData(){
        email = binding.email.text.toString().trim()
        password = binding.pwd.text.toString().trim()
        fullname = binding.fName.text.toString().trim()
        username = binding.uName.text.toString().trim()
        password2 = binding.pwd2.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            binding.pwd.error = "Please enter password"
        }
        else if (password.length < 6){
            binding.pwd.error = "Password must at least 6 characters long"
        }
        else if (password!=password2){
            binding.pwd2.error = "Confirmed password mismatched "
        }
        else {
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()
                database = FirebaseDatabase.getInstance().getReference("Users")
                val emailwithcomma = email?.replace('.', ',')
                val sampleImgUrl = "https://www.w3schools.com/howto/img_avatar.png"
                val User = User("guest", sampleImgUrl, username, fullname, emailwithcomma, password)
                if (emailwithcomma != null) {
                    database.child(emailwithcomma).setValue(User).addOnSuccessListener {
                        Toast.makeText(this, "Successfully saved your account", Toast.LENGTH_SHORT).show()
                    }
                }

                startActivity(Intent(this, ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "SignUp Fail due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}