package com.app.iot.ui.user

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.iot.databinding.ActivityUserListBinding
import com.app.iot.databinding.UsershownPopUpBinding

import com.app.iot.MainActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pop_up_window.view.okbutton
import kotlinx.android.synthetic.main.usershown_pop_up.view.*

class UserListActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityUserListBinding
    private lateinit var recordRecyclerview: RecyclerView
    private lateinit var binding2: UsershownPopUpBinding
    private lateinit var recordArrayList: ArrayList<RegisterUser>
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        binding2 =UsershownPopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        database = FirebaseDatabase.getInstance().getReference("Users")
        if (firebaseUser != null) {
            val email = firebaseUser.email
            if (email != null) {
                val emailwithcomma = email.replace('.', ',')
                database.child(emailwithcomma).get().addOnSuccessListener {
                    if (it.exists()) {

                        binding.emailTv.text = it.child("role").value as String
                    }
                }
            }
        }
        recordRecyclerview = binding.memberList
        recordRecyclerview.layoutManager = LinearLayoutManager(this)
        recordRecyclerview.setHasFixedSize(true)

        recordArrayList = arrayListOf<RegisterUser>()
        getMemberData()

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }



    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        super.onBackPressed()
    }

    private fun getMemberData() {

        var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("PI_002").child("HISTORY")

        reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //var i = 0
                if(snapshot.exists()){
                    //Toast.makeText(applicationContext,snapshot.toString(),Toast.LENGTH_SHORT).show()
                    for (childSnapshot in snapshot.children) {

                        var hstry = childSnapshot.child("History_Str").getValue().toString()//uid .toString()
                        // var ultrasonic = dataSnapshot.child("Ultrasonic").getValue().toString()
                        Toast.makeText(applicationContext,hstry.toString(),Toast.LENGTH_LONG).show()
                        Log.e("ONDATACHANGE",hstry.toString())
                        //val mem = userSnapshot.getValue(User::class.java)
//                        if (hstry != null) {
                        recordArrayList.add(RegisterUser(hstry))
//                        }
                    }
                    var adapter = UserAdapter1(recordArrayList)
                    recordRecyclerview.adapter = adapter
                    adapter.setOnItemClickListener(object : UserAdapter1.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //   val mDialogView = LayoutInflater.from(this@UserListActivity).inflate(R.layout, null)
                            val mDialogView=binding2.root
                            val mBuilder = AlertDialog.Builder(this@UserListActivity).setView(mDialogView).setTitle("Image")
                            val mAlertDialog = mBuilder.show()

                            //  Picasso.get().load(recordArrayList[position].avatar).resize(800,800).into(mDialogView.imageView)

                            mDialogView.okbutton.setOnClickListener{
                                mAlertDialog.dismiss()
                            }
                            mDialogView.deletebutton.setOnClickListener {
                                //   database.child(emailwithcomma).child("role").setValue("guest")
                                Toast.makeText(this@UserListActivity, "Add member successful", Toast.LENGTH_SHORT).show()
                                mAlertDialog.dismiss()
                                onBackPressed()
                            }
                            Toast.makeText(this@UserListActivity, "you clicked on item no. $position", Toast.LENGTH_SHORT).show()
                        }
                    })
//                        binding.username.text = childSnapshot.child("name").getValue().toString()
//
//
                }else{
                    Toast.makeText(applicationContext,"No Record",Toast.LENGTH_SHORT).show()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")

            }

        })

    }
}