package com.app.iot.ui.face

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityDetectPersonBinding
import com.app.iot.ui.face.FriendActivity
import com.app.iot.ui.face.NeighbourActivity


class DetectPersonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectPersonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetectPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonFriend.setOnClickListener {
            startActivity(Intent(this, FriendActivity::class.java))
            finish()
        }
        binding.buttonNeighbour.setOnClickListener {
            startActivity(Intent(this, NeighbourActivity::class.java))
            finish()
        }
        binding.btnCognate.setOnClickListener {
            startActivity(Intent(this, CognateActivity::class.java))
            finish()
        }
        binding.button10.setOnClickListener {
            startActivity(Intent(this, DeliveryActivity::class.java))
            finish()
        }
        binding.residency.setOnClickListener {
            startActivity(Intent(this, ResidencyActivity::class.java))
            finish()
        }
    }
}