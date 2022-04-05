package com.app.iot.ui.user

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.iot.MainActivity
import com.app.iot.databinding.ActivityUserList1Binding
import com.app.iot.databinding.ActivityUserListBinding
import com.app.iot.databinding.UsershownPopUpBinding
import com.app.iot.ui.face.DetectPersonActivity
import com.app.iot.ui.report.deleteMember

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pop_up_window.view.okbutton
import kotlinx.android.synthetic.main.usershown_pop_up.view.*

class KnownPerson : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityUserList1Binding
    private lateinit var recordRecyclerview: RecyclerView
    private lateinit var binding2: UsershownPopUpBinding
    private lateinit var recordArrayList: ArrayList<RegisterUser>
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserList1Binding.inflate(layoutInflater)
        binding2 =UsershownPopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.add.setOnClickListener {
             startActivity(Intent(this, AddRegister::class.java))
           finish()
         }

        binding.viewBtn.setOnClickListener {
            startActivity(Intent(this, DetectPersonActivity::class.java))
            finish()
        }
        binding.deleteBtn.setOnClickListener {
            startActivity(Intent(this, deleteMember::class.java))
            finish()
        }
        binding.button2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        super.onBackPressed()
    }


}