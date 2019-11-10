package com.example.calenderview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        set_event.setOnClickListener {
            val cal = Calendar.getInstance();
            val intent = Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", cal.getTimeInMillis());
            intent.putExtra("allDay", true);
            intent.putExtra("rrule", "FREQ=YEARLY");
            intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
            intent.putExtra("title", "A Test Event from android app");
            startActivity(intent);
        }
        /*set_event.setOnClickListener{
            val intent = Intent(Intent.ACTION_EDIT)
                .setData(CONTENT_URI)
                .putExtra(TITLE,"My Event")
                .putExtra(EVENT_LOCATION,"Here")
                .putExtra(CalendarContract.ACCOUNT_TYPE_LOCAL, "Local")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis()+ (60*60*1000))
            startActivity(intent)
        }*/
    }
}
