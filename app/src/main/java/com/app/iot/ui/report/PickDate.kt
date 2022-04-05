package com.app.iot.ui.report

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityPickDateBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import java.util.*

class PickDate : AppCompatActivity() {

    private lateinit var binding : ActivityPickDateBinding
    private lateinit var database : DatabaseReference

    var condition = false




    private var onDateSetListener: OnDateSetListener? = null
    //var chartDate: String? = null
    //var hourChart: String? = null
    var sMonth: String? = null
    var sDay: String? = null
    var chooseChart = 0


    var currentUserID: String? = null
    var fAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPickDateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val hourPick:NumberPicker = binding.hourPicker

        binding.tempdate.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal[Calendar.YEAR]
            val month = cal[Calendar.MONTH]
            val day = cal[Calendar.DAY_OF_MONTH]
            val dialog = DatePickerDialog(this@PickDate,
                onDateSetListener,
                year,
                month,
                day)
            //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        //Picking Date year month day
        onDateSetListener =  OnDateSetListener { view, year, month, dayOfMonth ->
            var month = month
            month = month + 1
            sMonth =month.toString()
            sDay =dayOfMonth.toString()
            chartDate = String.format(year.toString() + sMonth + sDay)
            binding.tempdate.setText(chartDate)
        }
        //Picking Hour
        hourPick.maxValue = 23
        hourPick.minValue = 0
        hourPick.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.hour.setText("Hour: $newVal")
            hourChart = newVal.toString()
        }
        binding.buttonProceed.setOnClickListener{

            if(binding.tempdate.text=="Select date"&&binding.hour.text=="Select hour"){
                binding.tempdate.setError("")
                binding.hour.setError("")
                return@setOnClickListener
            }else if(binding.hour.text=="Select hour"){
                binding.hour.setError("")
                binding.tempdate.setError(null)
                return@setOnClickListener
            }else if(binding.tempdate.text=="Select date"){
                binding.tempdate.setError("")
                binding.hour.setError(null)
                return@setOnClickListener
            }else{
                startActivity(Intent(applicationContext, linechart::class.java))
            }

        }
    }

//    fun proceed(view: View?) {
//        val i = Intent(this, LineChart::class.java)
//        startActivity(i)
//    }




    companion object {
        var chartDate: String? = null
        var hourChart: String? = null
        var choice = 0

    }
}