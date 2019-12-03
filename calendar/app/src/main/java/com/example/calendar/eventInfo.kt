package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.calenderview.R

class eventInfo : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_form)

        //val eventTitle : TextView = findViewById(R.id.eventFormText)
        //val editTitle : EditText = findViewById(R.id.editTitleText)
        //val saveButton : Button = findViewById(R.id.saveButton)
        val eventListButton : Button = findViewById(R.id.eventListButton)
        //val editText : EditText=findViewById(R.id.editText3)

        var mButton : Button = findViewById<Button>(R.id.eventListButton)
        mButton!!.setOnClickListener{
            // Create EventActivity
            val intentEvent = Intent(this@eventInfo, EventActivity::class.java)
            // Add possible intent data
            startActivity(intentEvent)
        }
    }

    @Override
    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
