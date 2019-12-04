package com.example.calendar

import android.annotation.TargetApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

class EventForm : AppCompatActivity(), View.OnClickListener {
    private val eventList = DataHolder.getInstance().events as ArrayList<Event>

    @TargetApi(24)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_form)

        val myToolBar: Toolbar = findViewById(R.id.monthlyToolBar)
        setSupportActionBar(myToolBar)

        val btnDate = findViewById<Button>(R.id.btn_date)
        val btnTime = findViewById<Button>(R.id.btn_time)
        val txtDate = findViewById<TextView>(R.id.in_date)
        val txtTime = findViewById<TextView>(R.id.in_time)
        val txtTitle = findViewById<EditText>(R.id.in_title)

        val btnSave = findViewById<Button>(R.id.saveButton)
        val btnList = findViewById<Button>(R.id.eventListButton)

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "EEE, MMM dd, yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            txtDate.text = sdf.format(cal.time)
        }

        btnDate.setOnClickListener{
            DatePickerDialog(this@EventForm, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)

            val myFormat = "hh:mm a"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            txtTime.text = sdf.format(cal.time)
        }

        btnTime.setOnClickListener{
            TimePickerDialog(this@EventForm, timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), false).show()
        }
        btnSave.setOnClickListener {
            if(txtTitle.text.toString() == "" || txtDate.text == "" || txtTime.text == ""){
                Toast.makeText(this@EventForm, "All Fields Must Be Filled", Toast.LENGTH_SHORT).show()
            }
            var boolean = true
            eventList.forEach{
                if(it.title.equals(txtTitle.text.toString())){
                    boolean = false
                    Toast.makeText(this@EventForm, "Duplicate Event Name!", Toast.LENGTH_SHORT).show()
                }
                if(it.calendar.time == cal.time){
                    boolean = false
                    Toast.makeText(this@EventForm, "Duplicate Event Time!", Toast.LENGTH_SHORT).show()
                }
            }
            if(boolean) {
                val myIntent = Intent(this@EventForm, EventActivity::class.java)
                myIntent.putExtra("event_title", txtTitle.text.toString())
                myIntent.putExtra("event_cal", cal)
                startActivity(myIntent)
            }
        }
        btnList.setOnClickListener {
            val myIntent = Intent(this@EventForm, EventActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun onClick(v: View) {
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu_event, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_to_month) {
            startActivity(Intent(this, MainActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
