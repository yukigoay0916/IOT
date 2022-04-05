package com.app.iot.ui.report

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityDeleteMemberBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage


class deleteMember : AppCompatActivity() {
    lateinit var binding : ActivityDeleteMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDeleteMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Toast.makeText(applicationContext,choice.toString(),Toast.LENGTH_SHORT).show()
        binding.buttonDelete2.setOnClickListener{
            if(binding.radioGroup.checkedRadioButtonId == -1){
                Toast.makeText(applicationContext,"Radio Button is Uncheck",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(binding.editTextName.text.isEmpty()){
                Toast.makeText(applicationContext,"Empty Field",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                // Delete the item and file stored
                var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()

                var have = 0
                if(deleteMember.Companion.choice==1){
                    reference.child("residency").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                have = 0
                                for (dataSnapshot in snapshot.children) {

                                    var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                                    if(name == binding.editTextName.text.toString()){
                                        have = 0
                                        var keys = dataSnapshot.key.toString()
                                        Toast.makeText(applicationContext,keys.toString(), Toast.LENGTH_SHORT).show()
                                        val storageReference = FirebaseStorage.getInstance().getReference("FaceToDetect/Residences/"+name+".png")
                                        storageReference.delete()
                                        reference.child("residency").child(keys).removeValue()


                                    }
                                }
                                if(have==1){
                                    Toast.makeText(applicationContext,"Successfully Deleted", Toast.LENGTH_SHORT).show()

                                }else{
                                    Toast.makeText(applicationContext,"No Such Name In This Category", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })

                }else if(deleteMember.Companion.choice==2){

                    reference.child("neighbour").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                have = 0
                                for (dataSnapshot in snapshot.children) {

                                    var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                                    if(name == binding.editTextName.text.toString()){
                                        have = 0
                                        var keys = dataSnapshot.key.toString()
                                        Toast.makeText(applicationContext,keys.toString(), Toast.LENGTH_SHORT).show()
                                        val storageReference = FirebaseStorage.getInstance().getReference("FaceToDetect/Neighbour/"+name+".png")
                                        storageReference.delete()
                                        reference.child("neighbour").child(keys).removeValue()


                                    }
                                }
                                if(have==1){
                                    Toast.makeText(applicationContext,"Successfully Deleted", Toast.LENGTH_SHORT).show()

                                }else{
                                    Toast.makeText(applicationContext,"No Such Name In This Category", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })

                }else if(deleteMember.Companion.choice==3){
                    reference.child("cognate").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                have = 0
                                for (dataSnapshot in snapshot.children) {

                                    var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                                    if(name == binding.editTextName.text.toString()){
                                        have = 0
                                        var keys = dataSnapshot.key.toString()
                                        Toast.makeText(applicationContext,keys.toString(), Toast.LENGTH_SHORT).show()
                                        val storageReference = FirebaseStorage.getInstance().getReference("FaceToDetect/Cognate/"+name+".png")
                                        storageReference.delete()
                                        reference.child("cognate").child(keys).removeValue()


                                    }
                                }
                                if(have==1){
                                    Toast.makeText(applicationContext,"Successfully Deleted", Toast.LENGTH_SHORT).show()

                                }else{
                                    Toast.makeText(applicationContext,"No Such Name In This Category", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })

                }else if(deleteMember.Companion.choice==4){
                    reference.child("friend").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                have = 0
                                for (dataSnapshot in snapshot.children) {

                                    var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                                    if(name == binding.editTextName.text.toString()){
                                        have = 0
                                        var keys = dataSnapshot.key.toString()
                                        Toast.makeText(applicationContext,keys.toString(), Toast.LENGTH_SHORT).show()
                                        val storageReference = FirebaseStorage.getInstance().getReference("FaceToDetect/Friend/"+name+".png")
                                        storageReference.delete()
                                        reference.child("friend").child(keys).removeValue()


                                    }
                                }
                                if(have==1){
                                    Toast.makeText(applicationContext,"Successfully Deleted", Toast.LENGTH_SHORT).show()

                                }else{
                                    Toast.makeText(applicationContext,"No Such Name In This Category", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })

                }else{
                    reference.child("deliveryman").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                have = 0
                                for (dataSnapshot in snapshot.children) {

                                    var name = dataSnapshot.child("name").getValue().toString() //Value but is only name
                                    if(name == binding.editTextName.text.toString()){
                                        have = 0
                                        var keys = dataSnapshot.key.toString()
                                        Toast.makeText(applicationContext,keys.toString(), Toast.LENGTH_SHORT).show()
                                        val storageReference = FirebaseStorage.getInstance().getReference("FaceToDetect/Deliveryman/"+name+".png")
                                        storageReference.delete()
                                        reference.child("deliveryman").child(keys).removeValue()


                                    }
                                }
                                if(have==1){
                                    Toast.makeText(applicationContext,"Successfully Deleted", Toast.LENGTH_SHORT).show()

                                }else{
                                    Toast.makeText(applicationContext,"No Such Name In This Category", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })

                }
            }
        }


    }


    fun chkResidence(view: View?) {
        choice = 1

    }

    fun chkNeighbour(view: View?) {
        choice = 2
    }

    fun chkCognate(view: View?) {
        choice = 3
    }
    fun chkFriend(view: View?) {
        choice = 4
    }

    fun chkDelivery(view: View?) {
        choice = 5
    }

    companion object {

        var choice: Int? = null

    }
}

