package com.app.iot.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.iot.databinding.ActivityListBinding
import com.app.iot.databinding.UserPopUpBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_pop_up.view.*


class ListActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var recordArrayList : ArrayList<User>
    private lateinit var recordRecyclerview : RecyclerView
    private lateinit var binding: ActivityListBinding
    private lateinit var binding2: UserPopUpBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListBinding.inflate(layoutInflater)
        binding2= UserPopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recordRecyclerview = binding.memberList
        recordRecyclerview.layoutManager = LinearLayoutManager(this)
        recordRecyclerview.setHasFixedSize(true)

//        firebaseAuth = FirebaseAuth.getInstance()
//        val firebaseUser = firebaseAuth.currentUser
//        database = FirebaseDatabase.getInstance().getReference("Users")
//        val email = firebaseUser?.email
//        binding.emailTv.text=email
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
        recordArrayList = arrayListOf<User>()
         getMemberData()

//        binding.vi.setOnClickListener {
//            startActivity(Intent(this, UserListActivity::class.java))
//            finish()
//        }

    }
    override fun onBackPressed() {
        startActivity(Intent(this, UserListActivity::class.java))
        finish()
        super.onBackPressed()
    }

    private fun getMemberData() {
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val mem = userSnapshot.getValue(User::class.java)
                        recordArrayList.add(mem!!)
                    }
//
//                    var adapter = UserAdapter(recordArrayList)
//                    recordRecyclerview.adapter = adapter
//                    adapter.setOnItemClickListener(object : UserAdapter.onItemClickListener{
//                        override fun onItemClick(position: Int) {
//                        //val mDialogView = LayoutInflater.from(this@ListActivity).inflate(binding2,null)
//                            val mDialogView = binding2.root
//
//                            val mBuilder = AlertDialog.Builder(this@ListActivity).setView(mDialogView).setTitle("Image")
//                            val mAlertDialog = mBuilder.show()
//
//                            Picasso.get().load(recordArrayList[position].avatar).resize(800,800).into(mDialogView.imageView)
//
//                            mDialogView.addtolistBtn.setOnClickListener{
//                                var emailwithcomma = recordArrayList[position].email.toString()
//                                emailwithcomma = emailwithcomma.replace('.',',')
//                                database.child(emailwithcomma).child("role").setValue("admin")
//                                Toast.makeText(this@ListActivity, "Add admin successful", Toast.LENGTH_SHORT).show()
//                                mAlertDialog.dismiss()
//                                onBackPressed()
//                            }
//                        }
//                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}