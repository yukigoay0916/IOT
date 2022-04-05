package com.app.iot.ui.report

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.iot.databinding.ActivityPiechartBinding

import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.*

class piechart : AppCompatActivity() {
    lateinit var binding : ActivityPiechartBinding
    lateinit var pielist: ArrayList<PieEntry>
    lateinit var pieDataset: PieDataSet
    lateinit var pieData: PieData
    var colors = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPiechartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Test Pie Chart
//        pielist = ArrayList()
//        pielist.add(PieEntry(0.3f, "Adam"))
//        pielist.add(PieEntry(0.7f, "Alan"))
//        pielist.add(PieEntry(0.2f, "YOYO"))
//        pielist.add(PieEntry(0.9f, "Aden"))
//        pielist.add(PieEntry(1f, "Eddy"))
//        Toast.makeText(applicationContext,pielist.toString(),Toast.LENGTH_SHORT).show()
//
//
//        for(i in ColorTemplate.MATERIAL_COLORS){
//            colors.add(i)
//        }
//
//        for(i in ColorTemplate.VORDIPLOM_COLORS){
//            colors.add(i)
//        }
//
//        pieDataset = PieDataSet(pielist, "Testing")
//        pieDataset.setColors(colors)
//
//        pieData = PieData(pieDataset)
//        pieData.setDrawValues(true)
//        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
//        pieData.setValueTextSize(12f)
//        pieData.setValueTextColor((Color.BLACK))
//
//        binding.pieChart.setData(pieData)
//        binding.pieChart.invalidate()

        if(PickYearMonth.Companion.choice ==1){
            Toast.makeText(applicationContext,"Choice 1 Residence", Toast.LENGTH_SHORT).show()
            var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("DetectedKnownHistory").child("Residence").child(
                PickYearMonth.Companion.yearmonth.toString())
            //.child(PickYearMonth.Companion.yearmonth.toString())
            reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){


                        Toast.makeText(applicationContext,snapshot.toString(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(applicationContext,snapshot.getValue().toString(), Toast.LENGTH_SHORT).show()

                        var values: String = snapshot.getValue().toString()
                        values = values.drop(1)
                        values = values.dropLast(1)
                        val delim = ","
                        var arr = values.split(delim).toTypedArray()
                        var i=0
                        pielist = ArrayList()
                        for( item in arr){
//                            var item = item.toString()
//                            Toast.makeText(applicationContext,arr[i],Toast.LENGTH_SHORT).show()
                            val delim2 = "="
                            var split2 = arr[i].split(delim2).toTypedArray()
                            //Name: split2[0]
                            //Count: split2[1]
                            Toast.makeText(applicationContext,split2[0].toString(), Toast.LENGTH_SHORT).show()
                            Toast.makeText(applicationContext,split2[1].toString(), Toast.LENGTH_SHORT).show()
                            pielist.add(PieEntry(split2[1].toFloat(), split2[0]))
                            Toast.makeText(applicationContext,pielist.toString(), Toast.LENGTH_SHORT).show()
                            i++
                        }

                        for(i in ColorTemplate.MATERIAL_COLORS){
                            colors.add(i)
                        }
                        for(i in ColorTemplate.VORDIPLOM_COLORS){
                            colors.add(i)
                        }

                        pieDataset = PieDataSet(pielist, "Frequenct People Visit")
                        pieDataset.setColors(colors)

                        pieData = PieData(pieDataset)
                        pieData.setDrawValues(true)
                        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
                        pieData.setValueTextSize(12f)
                        pieData.setValueTextColor((Color.BLACK))

                        binding.pieChart.setData(pieData)
                        binding.pieChart.invalidate()

                        //Toast.makeText(applicationContext,values,Toast.LENGTH_SHORT).show()


                    }else{
                        Toast.makeText(applicationContext,"No Record On This Month", Toast.LENGTH_SHORT).show()
                    }
                    //var ma : String = snapshot.getValue().toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        }else if(PickYearMonth.Companion.choice ==2){

            Toast.makeText(applicationContext,"Choice 2 Neighbour", Toast.LENGTH_SHORT).show()
            var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("DetectedKnownHistory").child("Neighbour").child(
                PickYearMonth.Companion.yearmonth.toString())
            //.child(PickYearMonth.Companion.yearmonth.toString())
            reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){


                        Toast.makeText(applicationContext,snapshot.toString(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(applicationContext,snapshot.getValue().toString(), Toast.LENGTH_SHORT).show()

                        var values: String = snapshot.getValue().toString()
                        values = values.drop(1)
                        values = values.dropLast(1)
                        val delim = ","
                        var arr = values.split(delim).toTypedArray()
                        var i=0
                        pielist = ArrayList()
                        for( item in arr){
//                            var item = item.toString()
//                            Toast.makeText(applicationContext,arr[i],Toast.LENGTH_SHORT).show()
                            val delim2 = "="
                            var split2 = arr[i].split(delim2).toTypedArray()
                            //Name: split2[0]
                            //Count: split2[1]
                            Toast.makeText(applicationContext,split2[0].toString(), Toast.LENGTH_SHORT).show()
                            Toast.makeText(applicationContext,split2[1].toString(), Toast.LENGTH_SHORT).show()
                            pielist.add(PieEntry(split2[1].toFloat(), split2[0]))
                            Toast.makeText(applicationContext,pielist.toString(), Toast.LENGTH_SHORT).show()
                            i++
                        }

                        for(i in ColorTemplate.MATERIAL_COLORS){
                            colors.add(i)
                        }
                        for(i in ColorTemplate.VORDIPLOM_COLORS){
                            colors.add(i)
                        }

                        pieDataset = PieDataSet(pielist, "Frequenct People Visit")
                        pieDataset.setColors(colors)

                        pieData = PieData(pieDataset)
                        pieData.setDrawValues(true)
                        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
                        pieData.setValueTextSize(12f)
                        pieData.setValueTextColor((Color.BLACK))

                        binding.pieChart.setData(pieData)
                        binding.pieChart.invalidate()

                        //Toast.makeText(applicationContext,values,Toast.LENGTH_SHORT).show()


                    }else{
                        Toast.makeText(applicationContext,"No Record On This Month", Toast.LENGTH_SHORT).show()
                    }
                    //var ma : String = snapshot.getValue().toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }else if(PickYearMonth.Companion.choice ==3){
            Toast.makeText(applicationContext,"Choice 3 Cognate", Toast.LENGTH_SHORT).show()
            var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("DetectedKnownHistory").child("Cognate").child(
                PickYearMonth.Companion.yearmonth.toString())
            //.child(PickYearMonth.Companion.yearmonth.toString())
            reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){


                        Toast.makeText(applicationContext,snapshot.toString(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(applicationContext,snapshot.getValue().toString(), Toast.LENGTH_SHORT).show()

                        var values: String = snapshot.getValue().toString()
                        values = values.drop(1)
                        values = values.dropLast(1)
                        val delim = ","
                        var arr = values.split(delim).toTypedArray()
                        var i=0
                        pielist = ArrayList()
                        for( item in arr){
//                            var item = item.toString()
//                            Toast.makeText(applicationContext,arr[i],Toast.LENGTH_SHORT).show()
                            val delim2 = "="
                            var split2 = arr[i].split(delim2).toTypedArray()
                            //Name: split2[0]
                            //Count: split2[1]
                            Toast.makeText(applicationContext,split2[0].toString(), Toast.LENGTH_SHORT).show()
                            Toast.makeText(applicationContext,split2[1].toString(), Toast.LENGTH_SHORT).show()
                            pielist.add(PieEntry(split2[1].toFloat(), split2[0]))
                            Toast.makeText(applicationContext,pielist.toString(), Toast.LENGTH_SHORT).show()
                            i++
                        }

                        for(i in ColorTemplate.MATERIAL_COLORS){
                            colors.add(i)
                        }
                        for(i in ColorTemplate.VORDIPLOM_COLORS){
                            colors.add(i)
                        }

                        pieDataset = PieDataSet(pielist, "Frequenct People Visit")
                        pieDataset.setColors(colors)

                        pieData = PieData(pieDataset)
                        pieData.setDrawValues(true)
                        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
                        pieData.setValueTextSize(12f)
                        pieData.setValueTextColor((Color.BLACK))

                        binding.pieChart.setData(pieData)
                        binding.pieChart.invalidate()

                        //Toast.makeText(applicationContext,values,Toast.LENGTH_SHORT).show()


                    }else{
                        Toast.makeText(applicationContext,"No Record On This Month", Toast.LENGTH_SHORT).show()
                    }
                    //var ma : String = snapshot.getValue().toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }else if(PickYearMonth.Companion.choice ==4){
            Toast.makeText(applicationContext,"Choice 4 Friend", Toast.LENGTH_SHORT).show()
            var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("DetectedKnownHistory").child("Friend").child(
                PickYearMonth.Companion.yearmonth.toString())
            //.child(PickYearMonth.Companion.yearmonth.toString())
            reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){


                        Toast.makeText(applicationContext,snapshot.toString(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(applicationContext,snapshot.getValue().toString(), Toast.LENGTH_SHORT).show()

                        var values: String = snapshot.getValue().toString()
                        values = values.drop(1)
                        values = values.dropLast(1)
                        val delim = ","
                        var arr = values.split(delim).toTypedArray()
                        var i=0
                        pielist = ArrayList()
                        for( item in arr){
//                            var item = item.toString()
//                            Toast.makeText(applicationContext,arr[i],Toast.LENGTH_SHORT).show()
                            val delim2 = "="
                            var split2 = arr[i].split(delim2).toTypedArray()
                            //Name: split2[0]
                            //Count: split2[1]
                            Toast.makeText(applicationContext,split2[0].toString(), Toast.LENGTH_SHORT).show()
                            Toast.makeText(applicationContext,split2[1].toString(), Toast.LENGTH_SHORT).show()
                            pielist.add(PieEntry(split2[1].toFloat(), split2[0]))
                            Toast.makeText(applicationContext,pielist.toString(), Toast.LENGTH_SHORT).show()
                            i++
                        }

                        for(i in ColorTemplate.MATERIAL_COLORS){
                            colors.add(i)
                        }
                        for(i in ColorTemplate.VORDIPLOM_COLORS){
                            colors.add(i)
                        }

                        pieDataset = PieDataSet(pielist, "Frequenct People Visit")
                        pieDataset.setColors(colors)

                        pieData = PieData(pieDataset)
                        pieData.setDrawValues(true)
                        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
                        pieData.setValueTextSize(12f)
                        pieData.setValueTextColor((Color.BLACK))

                        binding.pieChart.setData(pieData)
                        binding.pieChart.invalidate()

                        //Toast.makeText(applicationContext,values,Toast.LENGTH_SHORT).show()


                    }else{
                        Toast.makeText(applicationContext,"No Record On This Month", Toast.LENGTH_SHORT).show()
                    }
                    //var ma : String = snapshot.getValue().toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        }else{
            Toast.makeText(applicationContext,"Choice 5 DeliveryMan", Toast.LENGTH_SHORT).show()
            var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("DetectedKnownHistory").child("DeliveryMan").child(
                PickYearMonth.Companion.yearmonth.toString())
            //.child(PickYearMonth.Companion.yearmonth.toString())
            reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){


                        Toast.makeText(applicationContext,snapshot.toString(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(applicationContext,snapshot.getValue().toString(), Toast.LENGTH_SHORT).show()

                        var values: String = snapshot.getValue().toString()
                        values = values.drop(1)
                        values = values.dropLast(1)
                        val delim = ","
                        var arr = values.split(delim).toTypedArray()
                        var i=0
                        pielist = ArrayList()
                        for( item in arr){
//                            var item = item.toString()
//                            Toast.makeText(applicationContext,arr[i],Toast.LENGTH_SHORT).show()
                            val delim2 = "="
                            var split2 = arr[i].split(delim2).toTypedArray()
                            //Name: split2[0]
                            //Count: split2[1]
                            Toast.makeText(applicationContext,split2[0].toString(), Toast.LENGTH_SHORT).show()
                            Toast.makeText(applicationContext,split2[1].toString(), Toast.LENGTH_SHORT).show()
                            pielist.add(PieEntry(split2[1].toFloat(), split2[0]))
                            Toast.makeText(applicationContext,pielist.toString(), Toast.LENGTH_SHORT).show()
                            i++
                        }

                        for(i in ColorTemplate.MATERIAL_COLORS){
                            colors.add(i)
                        }
                        for(i in ColorTemplate.VORDIPLOM_COLORS){
                            colors.add(i)
                        }

                        pieDataset = PieDataSet(pielist, "Frequenct People Visit")
                        pieDataset.setColors(colors)

                        pieData = PieData(pieDataset)
                        pieData.setDrawValues(true)
                        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
                        pieData.setValueTextSize(12f)
                        pieData.setValueTextColor((Color.BLACK))

                        binding.pieChart.setData(pieData)
                        binding.pieChart.invalidate()

                        //Toast.makeText(applicationContext,values,Toast.LENGTH_SHORT).show()


                    }else{
                        Toast.makeText(applicationContext,"No Record On This Month", Toast.LENGTH_SHORT).show()
                    }
                    //var ma : String = snapshot.getValue().toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}