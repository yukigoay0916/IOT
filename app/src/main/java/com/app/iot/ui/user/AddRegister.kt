package com.app.iot.ui.user

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.MainActivity
import com.app.iot.databinding.ActivityAddRegisterBinding
import com.app.iot.ui.Register

import com.app.iot.ui.visitor.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*

class AddRegister : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database : DatabaseReference

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    var selected : Uri? = null

    private var username = ""
    private lateinit var binding: ActivityAddRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button5.setOnClickListener{
            if(binding.radioGroup.checkedRadioButtonId == -1){
                Toast.makeText(applicationContext,"Radio Button is Uncheck", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.editTextTextPersonName2.toString())){
                binding.editTextTextPersonName2.error="Please enter your name first "
                return@setOnClickListener
            } else{
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }
        binding.imageView7.setOnClickListener {
            uploadImage()
        }
        binding.buttonback.setOnClickListener {
            startActivity(Intent(this, KnownPerson::class.java))
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
            val imageViewPhoto : ImageView = binding.imageView7
            imageViewPhoto.setImageBitmap(bmp)
        }
        if(requestCode == 111 && resultCode == Activity.RESULT_OK && data != null){
            var name = binding.editTextTextPersonName2.text.toString().trim()
            if(!TextUtils.isEmpty(name)){
                   if (choice!!<=5 && choice!!>=1){
                selected = data.data
                Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show()
                var name = binding.editTextTextPersonName2.text.toString().trim()
                name=name.replace("\\s".toRegex(), "")
                database = FirebaseDatabase.getInstance().getReference(AddRegister.Companion.path!!)
                val people = RegisterUser(name)
                database.push().setValue(people)
                val filen=AddRegister.Companion.identity
                val storageReference = FirebaseStorage.getInstance().getReference("FaceToDetect/$filen/$name.png")
                storageReference.putFile(selected!!)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this, ProfileActivity::class.java))
                            }
                        }
                    }
            }
            }


//            progressDialog = ProgressDialog(this)
//            progressDialog.setMessage("Uploading image")
//            progressDialog.setCancelable(false)
//            progressDialog.show()
//            builder.setPositiveButton("Yes") { dialogInterface, which ->
//                firebaseAuth.signOut()
//                checkUser()


//            name = binding.editTextTextPersonName2.text.toString().trim()
//            database = FirebaseDatabase.getInstance().getReference("Users")
//            val User = RegisterUser(name)
//
//            database.child(name).setValue(User).addOnSuccessListener {
//                Toast.makeText(this, "Successfully saved your account", Toast.LENGTH_SHORT).show()
////            }
//            database = FirebaseDatabase.getInstance().getReference(AddRegister.Companion.path!!)
//            val people = RegisterUser(name)
//            database.push().setValue(people)
//            val people2 = RegisterUser("Solou")
//            database.push().setValue(people2)




    fun radResidence(view: View?) {
        choice = 1
        identity="Residences"
        path="residences"
    }

    fun radNeighbour(view: View?) {
        choice = 2
        identity="Neighbour"
        path="neighbour"
    }

    fun radCognate(view: View?) {
        choice = 3
        identity="Cognate"
        path="cognate"
    }
    fun radFriend(view: View?) {
        choice = 4
        identity="Friend"
        path="friend"
    }

    fun radDelivery(view: View?) {
        choice = 5
        identity="Deliveryman"
        path="deliveryman"
    }

    companion object {
        var identity: String? = null
        var path: String? = null
        var choice: Int? = null

    }
}