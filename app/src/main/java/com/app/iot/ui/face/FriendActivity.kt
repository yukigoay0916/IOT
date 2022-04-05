package com.app.iot.ui.face

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.iot.MainActivity
import com.app.iot.databinding.ActivityFriendyBinding
import com.app.iot.databinding.UsershownPopUpBinding
import com.app.iot.ui.user.KnownPerson
import com.app.iot.ui.user.ListActivity
import com.app.iot.ui.user.RegisterUser
import com.app.iot.ui.user.UserAdapter1

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.usershown_pop_up.view.*

class FriendActivity : AppCompatActivity() {

        private lateinit var database: DatabaseReference
        private lateinit var binding: ActivityFriendyBinding
        private lateinit var recordRecyclerview: RecyclerView
        private lateinit var binding2: UsershownPopUpBinding
        private lateinit var recordArrayList: ArrayList<RegisterUser>
        private lateinit var firebaseAuth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityFriendyBinding.inflate(layoutInflater)
            binding2 = UsershownPopUpBinding.inflate(layoutInflater)
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

            binding.viewBtn.setOnClickListener {
                startActivity(Intent(this, KnownPerson::class.java))
                finish()
            }


        }

        override fun onBackPressed() {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            super.onBackPressed()
        }

        private fun getMemberData() {

            var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("friend")

            reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //var i = 0
                    if(snapshot.exists()){
                        //Toast.makeText(applicationContext,snapshot.toString(),Toast.LENGTH_SHORT).show()
                        for (childSnapshot in snapshot.children) {

                            var hstry = childSnapshot.child("name").getValue().toString()//uid .toString()
                            // var ultrasonic = dataSnapshot.child("Ultrasonic").getValue().toString()
                            Toast.makeText(applicationContext,hstry.toString(), Toast.LENGTH_LONG).show()
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
                                val mBuilder = AlertDialog.Builder(this@FriendActivity).setView(mDialogView).setTitle("Image")
                                val mAlertDialog = mBuilder.show()

                                //  Picasso.get().load(recordArrayList[position].avatar).resize(800,800).into(mDialogView.imageView)

                                mDialogView.okbutton.setOnClickListener{
                                    mAlertDialog.dismiss()
                                }
                                mDialogView.deletebutton.setOnClickListener {
                                    //   database.child(emailwithcomma).child("role").setValue("guest")
                                    Toast.makeText(this@FriendActivity, "Add member successful", Toast.LENGTH_SHORT).show()
                                    mAlertDialog.dismiss()
                                    onBackPressed()
                                }
                                Toast.makeText(this@FriendActivity, "you clicked on item no. $position", Toast.LENGTH_SHORT).show()
                            }
                        })
//                        binding.username.text = childSnapshot.child("name").getValue().toString()
//
//
                    }else{
                        Toast.makeText(applicationContext,"No Record", Toast.LENGTH_SHORT).show()
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")

                }

            })

        }
}
