package com.example.calendar

import android.annotation.TargetApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class EventForm : AppCompatActivity(), View.OnClickListener {
    /*private val mYear: Int = 0
    private val mMonth: Int = 0
    private val mDay: Int = 0
    private val mHour: Int = 0
    private val mMinute: Int = 0*/

    @TargetApi(24)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_form)

        val btnDate = findViewById<Button>(R.id.btn_date)
        val btnTime = findViewById<Button>(R.id.btn_time)
        val txtDate = findViewById<TextView>(R.id.in_date)
        val txtTime = findViewById<TextView>(R.id.in_time)
        val txtTitle = findViewById<EditText>(R.id.in_title)

        val btnSave = findViewById<Button>(R.id.saveButton)

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
            val myEvent = Event(txtTitle.toString(), cal)
            val myIntent = Intent(this@EventForm, EventActivity::class.java)
            //myIntent.putExtra("NewEvent", myEvent)
        }
    }

    override fun onClick(v: View) {
    }
}
