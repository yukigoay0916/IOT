package com.app.iot.ui.report
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityLinechartBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.database.*

class linechart : AppCompatActivity() {
    lateinit var binding: ActivityLinechartBinding
    //Test LineChart
    lateinit var linelist: ArrayList<Entry>
    lateinit var lineDataset: LineDataSet
    lateinit var lineData: LineData

    var xsize = 0
    var ysize = 0
    var y = 0.0
    var xValue = ArrayList<Int>()
    var yValue = ArrayList<Double>()

    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinechartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Test Line Chart
//        linelist = ArrayList()
//        linelist.add(Entry(10f,100f))
//        linelist.add(Entry(20f,300f))
//        linelist.add(Entry(30f,200f))
//        linelist.add(Entry(40f,600f))
//        linelist.add(Entry(50f,500f))
//        linelist.add(Entry(50f,900f))
//
//        lineDataset = LineDataSet(linelist,"Count")
//        lineData = LineData(lineDataset)
//        binding.lineChart.data = lineData
//
//        lineDataset.valueTextColor = Color.BLUE
//        lineDataset.valueTextSize = 20f

        //Real Line Chart
        var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("minutesReport").child(
            PickDate.chartDate.toString()).child(PickDate.hourChart.toString())



        reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //var i = 0
                if(snapshot.exists()){
                    //Toast.makeText(applicationContext,snapshot.toString(),Toast.LENGTH_SHORT).show()
                    for (childSnapshot in snapshot.children) {
                        var minutes = childSnapshot.key.toString()
                        //Toast.makeText(applicationContext,minutes,Toast.LENGTH_SHORT).show()
                        var x = minutes.toInt()


//                    Toast.makeText(applicationContext,x.toString(),Toast.LENGTH_SHORT).show()
                        if (x != null) {
                            xValue.add(x)
//                        Toast.makeText(applicationContext,xValue.toString(),Toast.LENGTH_SHORT).show()
//
//                        Toast.makeText(applicationContext,xValue[i++].toString(),Toast.LENGTH_SHORT).show()
                        }
                        xsize++
                        //Toast.makeText(applicationContext,xsize.toString(),Toast.LENGTH_SHORT).show()

                    }
                }else{
                    Toast.makeText(applicationContext,"No Record", Toast.LENGTH_SHORT).show()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")

            }

        })

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //var j = 0
                if(snapshot.exists()){
                    //Toast.makeText(applicationContext,snapshot.toString(),Toast.LENGTH_SHORT).show()
                    for (dataSnapshot in snapshot.children) {
//                        //var ultrasonic = childSnapshot.child("ultrasonic").getValue().toString()
//                        //Toast.makeText(applicationContext,ultrasonic, Toast.LENGTH_SHORT).show()
//
                        var ultrasonic = dataSnapshot.child("Ultrasonic").getValue().toString() //Value but is only ultrasonic
                        //Toast.makeText(applicationContext,ultrasonic, Toast.LENGTH_SHORT).show()

                        try {
                            y = ultrasonic!!.toDouble()
                            yValue.add(y)
                            //Toast.makeText(applicationContext,yValue.toString(),Toast.LENGTH_SHORT).show()
                            //Toast.makeText(applicationContext,yValue[j++].toString(),Toast.LENGTH_SHORT).show()
                            ysize++
                        } catch (e: NullPointerException) {
                            ysize--
                            //Toast.makeText(applicationContext,"Error"+ yValue.toString(),Toast.LENGTH_SHORT).show()
                            break
                        }
                    }

                    ////printValue()
                    //Toast.makeText(applicationContext,dataValues().toString(),Toast.LENGTH_SHORT).show()
                    //Real
                    lineDataset = LineDataSet(dataValues(), "Ultrasonic Status ON "+ PickDate.chartDate.toString()+ PickDate.hourChart.toString())
                    lineData = LineData(lineDataset)
                    binding.lineChart.data = lineData
                    lineDataset.valueTextColor = Color.BLUE
                    lineDataset.valueTextSize = 20f
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun printValue() {
        Toast.makeText(applicationContext,xValue[0].toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(applicationContext,yValue.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(applicationContext,xsize.toString(), Toast.LENGTH_SHORT).show()
    }
    private fun dataValues(): ArrayList<Entry>? {
        val dataVals = java.util.ArrayList<Entry>()
        for (j in 0 until xsize) {
            val newX = xValue[j]
            val newY = yValue[j]
            dataVals.add(Entry(newX.toFloat(), newY.toFloat()))
        }
        return dataVals
    }



}
