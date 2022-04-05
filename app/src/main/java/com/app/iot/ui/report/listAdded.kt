package com.app.iot.ui.report

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityListAddedBinding

import com.app.iot.ui.user.AddRegister
import com.google.firebase.database.*

class listAdded : AppCompatActivity() {
    lateinit var binding: ActivityListAddedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAddedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        reference.child("residency").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (dataSnapshot in snapshot.children) {
                        var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                        //Toast.makeText(applicationContext,ultrasonic, Toast.LENGTH_SHORT).show()
                        binding.textViewRes.append(name)
                        binding.textViewRes.append("\n")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        reference.child("neighbour").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (dataSnapshot in snapshot.children) {
                        var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                        //Toast.makeText(applicationContext,ultrasonic, Toast.LENGTH_SHORT).show()
                        binding.textViewNei.append(name)
                        binding.textViewNei.append("\n")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        reference.child("cognate").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (dataSnapshot in snapshot.children) {
                        var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                        //Toast.makeText(applicationContext,ultrasonic, Toast.LENGTH_SHORT).show()
                        binding.textViewCog.append(name)
                        binding.textViewCog.append("\n")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        reference.child("friend").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (dataSnapshot in snapshot.children) {
                        var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                        //Toast.makeText(applicationContext,ultrasonic, Toast.LENGTH_SHORT).show()
                        binding.textViewFr.append(name)
                        binding.textViewFr.append("\n")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        reference.child("deliveryman").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (dataSnapshot in snapshot.children) {
                        var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                        //Toast.makeText(applicationContext,ultrasonic, Toast.LENGTH_SHORT).show()
                        binding.textViewDeli.append(name)
                        binding.textViewDeli.append("\n")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })


        binding.buttonAdd.setOnClickListener{
            startActivity(Intent(applicationContext, AddRegister::class.java))
        }

        binding.buttonDelete.setOnClickListener{
            startActivity(Intent(applicationContext, deleteMember::class.java))
        }


    }
}