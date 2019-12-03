package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast

import com.example.calenderview.R
import java.util.Calendar

class EventForm : AppCompatActivity(), View.OnClickListener {
    private val mYear: Int = 0
    private val mMonth: Int = 0
    private val mDay: Int = 0
    private val mHour: Int = 0
    private val mMinute: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_form)

        val btnDate = findViewById<Button>(R.id.btn_date)
        val btnTime = findViewById<Button>(R.id.btn_time)
        val txtDate = findViewById<TextView>(R.id.in_date)
        val txtTime = findViewById<TextView>(R.id.in_time)
        btnDate.setOnClickListener{
            Toast.makeText(this@EventForm, "Hello", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        Toast.makeText(this@EventForm, "Hello", Toast.LENGTH_SHORT).show()
    }


    /*@Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            Toast t = Toast.makeText(this, "Hello", Toast.LENGTH_LONG);
            t.show();
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }*/
}
