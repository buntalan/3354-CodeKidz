package com.example.calendar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnLongClickListener {
    var msg: String? = null
    var datePass: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myToolBar: Toolbar = findViewById(R.id.monthlyToolBar)
        setSupportActionBar(myToolBar)


        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
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
        //val intentWeek = Intent(this@MainActivity, ViewWeekly::class.java)
        // Pass intent some data
        // intent.puttExtra()
        //startActivity(intent)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu_month, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_to_week) {
            startActivity(ViewWeekly.newIntent(this, calendarView.date))
            return true
        }
        if (id == R.id.menu_to_event) {
            startActivity(Intent(this@MainActivity, eventForm::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
