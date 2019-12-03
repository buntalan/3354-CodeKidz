package com.example.addevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class eventInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)

        val textView : TextView = findViewById(R.id.textView)
        val editText :EditText=findViewById(R.id.editText)
        //val editText : EditText=findViewById(R.id.editText3)
        
    }
}
