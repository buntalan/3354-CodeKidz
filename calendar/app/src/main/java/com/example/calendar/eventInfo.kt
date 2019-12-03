package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.calenderview.R

class eventInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event_form)

        val eventTitle : TextView = findViewById(R.id.eventFormText)
        val editTitle : EditText=findViewById(R.id.editTitleText)
        //val editText : EditText=findViewById(R.id.editText3)

    }
}
