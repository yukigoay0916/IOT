package com.app.iot.ui.report

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityPickYearMonthBinding
import java.util.*

class PickYearMonth : AppCompatActivity() {
    lateinit var binding: ActivityPickYearMonthBinding

    private var onDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var sMonth: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickYearMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.YMCal.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal[Calendar.YEAR]
            val month = cal[Calendar.MONTH]
            val day = cal[Calendar.DAY_OF_MONTH]
            val dialog = DatePickerDialog(this@PickYearMonth,
                onDateSetListener,
                year,
                month,
                day)
            //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            var month = month
            month = month + 1
            sMonth = month.toString()
            yearmonth = String.format(year.toString() + sMonth)
            binding.YMCal.setText(yearmonth)
        }
        binding.buttonCheck.setOnClickListener{
            if(binding.radioGroup.checkedRadioButtonId == -1){
                Toast.makeText(applicationContext,"Radio Button is Uncheck",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(binding.YMCal.text=="Select date"){
                Toast.makeText(applicationContext,"Choose Date",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                startActivity(Intent(applicationContext, piechart::class.java))
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
        var yearmonth: String? = null
        var choice: Int? = null

    }
}