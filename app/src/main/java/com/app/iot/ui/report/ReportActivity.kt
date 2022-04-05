package com.app.iot.ui.report

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityReportBinding


class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pie.setOnClickListener {
            startActivity(Intent(this, PickYearMonth::class.java))
            finish()
        }
        binding.line.setOnClickListener {
            startActivity(Intent(this, PickDate::class.java))
            finish()
        }
    }
}