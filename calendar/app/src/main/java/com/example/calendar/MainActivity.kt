package com.example.calenderview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import com.example.calendar.ViewWeekly

class MainActivity : AppCompatActivity() {
    var msg: String? = null;
    var mButton: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

        //calendarView?.setOnClickListener(new)

        mButton = findViewById(R.id.buttonTest) as Button
        mButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, ViewWeekly::class.java)
            //intent.putExtra();
            startActivity(intent)
        }


    }
}
