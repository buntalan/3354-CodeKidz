package com.example.materialeventcalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.materialeventcalendar.CalendarView
import com.example.materialeventcalendar.EventItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eventList: ArrayList<EventItem> = arrayListOf()
        eventList.add(EventItem("21-11-2019", "22-11-2019", "Software Engineering Class", "#EEFFCC", "1"))
        eventList.add(EventItem("24-11-2019", "26-11-2019", "Data Structures Class"))
        eventCalendar.setCalendarEventClickListener(object : CalendarView.CalendarEventClickListener {
            override fun onEventClick(eventItem: EventItem) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        eventCalendar.addEventList(eventList)
    }
}