package com.example.calendar

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.calenderview.R

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val listView = findViewById<ListView>(R.id.main_listview)

        listView.adapter = MyCustomAdapter(this)//custom adapter
    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext : Context
        private val eventList = arrayListOf<Event>()

        init {
            mContext = context
        }
        //ignore for now
        override fun getItem(position: Int): Any { return "TEST STRING" }
        override fun getItemId(position: Int): Long { return position.toLong() }

        //how many rows in the list
        override fun getCount(): Int {
            return 7
        }

        //Renders out each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            //val textView = TextView(mContext)
            //textView.text = "Here is my Row for my ListView"
            //return textView

            val layoutInflator = LayoutInflater.from(mContext)
            val rowMain = layoutInflator.inflate(R.layout.activity_event_row, viewGroup, false)

            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textview)
            nameTextView.text = "Event Title"
            val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)
            positionTextView.text = "Event Number: $position"
            return rowMain
        }
    }
}