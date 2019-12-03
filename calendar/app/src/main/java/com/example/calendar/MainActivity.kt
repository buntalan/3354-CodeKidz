package com.example.calenderview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import com.example.calendar.EventDay
import com.example.calendar.ViewWeekly
import com.example.calendar.eventInfo

class MainActivity : AppCompatActivity(), View.OnLongClickListener, View.OnClickListener{
    var msg: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            msg = "Selected date is " + (month + 1) +  "/" + dayOfMonth + "/" + year
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

    }

    @Override
    override fun onClick(v: View?) {
        val intentDay = Intent(this@MainActivity, EventDay::class.java)
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
