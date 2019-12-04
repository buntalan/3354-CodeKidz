package com.example.calendar

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.calendar.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventActivity : AppCompatActivity() {
    public val eventList = arrayListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        if(intent.hasExtra("event_title")){
            val newEvent = Event(intent.getStringExtra("event_title"), intent.getSerializableExtra("event_cal") as Calendar)
            eventList.add(newEvent)
        }
        val listView = findViewById<ListView>(R.id.main_listview)
        listView.adapter = MyCustomAdapter(this, eventList)//custom adapter
        listView.setOnItemClickListener{parent, view, position, id ->
            val t = Toast.makeText(this@EventActivity, "Hello", Toast.LENGTH_LONG)
            t.show()
        }
    }

    private class MyCustomAdapter(context: Context, events: ArrayList<Event>): BaseAdapter() {

        private val mContext : Context
        private val eventList = events

        init {
            mContext = context
        }
        //ignore for now
        override fun getItem(position: Int): Any { return "TEST STRING" }
        override fun getItemId(position: Int): Long { return position.toLong() }

        //how many rows in the list
        override fun getCount(): Int {
            return eventList.size
        }

        //Renders out each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            //val textView = TextView(mContext)
            //textView.text = "Here is my Row for my ListView"
            //return textView

            val layoutInflator = LayoutInflater.from(mContext)
            val rowMain = layoutInflator.inflate(R.layout.activity_event_row, viewGroup, false)

            val currentEvent = eventList[position]
            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textview)
            nameTextView.text = currentEvent.title

            val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)
            val myFormat = "EEE, MMM dd, yyyy\t\t\thh:mm a"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            positionTextView.text = sdf.format(currentEvent.calendar.time)
            return rowMain
        }
    }
}