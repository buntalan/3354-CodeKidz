package com.example.calenderview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.calendar.ViewWeekly
import com.example.calendar.eventInfo

class MainActivity : AppCompatActivity(), View.OnLongClickListener {
    var msg: String? = null
    var mButtonEvent: Button? = null
    var mButtonWeekly: Button? = null
    var datePass: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            msg = "Selected date is " + (month + 1) + "/"  + dayOfMonth + "/" + year
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

        mButtonEvent = findViewById<Button>(R.id.buttonEvent)
        mButtonEvent!!.setOnClickListener{
            // Create EventActivity
            val intentEvent = Intent(this@MainActivity, eventInfo::class.java)
            // Add possible intent data
            startActivity(intentEvent)
        }


        mButtonWeekly = findViewById<Button>(R.id.buttonWeek)
        mButtonWeekly!!.setOnClickListener {
            val intentWeekly = ViewWeekly.newIntent(this@MainActivity, calendarView.date)
            startActivity(intentWeekly)
        }

//        // Change activity to ViewWeekly on long press
//        mButtonWeekly!!.setOnLongClickListener {
//            Toast.makeText(this, "LongClick works!, ", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this@MainActivity, ViewWeekly::class.java)
//            //intent.putExtra();
//            startActivity(intent)
//
//            true
//        }

    }

    @Override
    override fun onLongClick(p0: View?): Boolean {
        val intentWeek = Intent(this@MainActivity, ViewWeekly::class.java)
        // Pass intent some data
        // intent.puttExtra()
        startActivity(intent)
        return true
    }
}
