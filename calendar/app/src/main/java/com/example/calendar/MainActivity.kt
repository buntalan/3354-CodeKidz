package com.example.calenderview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.calendar.ViewWeekly

class MainActivity : AppCompatActivity(), View.OnLongClickListener {
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
        // Change activity to ViewWeekly on long press
        calendarView.setOnLongClickListener {
            Toast.makeText(this, "LongClick works!, ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, ViewWeekly::class.java)
            //intent.putExtra();
            startActivity(intent)

            true
        }

        mButton = findViewById(R.id.buttonTest) as Button
        mButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, ViewWeekly::class.java)
            //intent.putExtra();
            startActivity(intent)
        }



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
