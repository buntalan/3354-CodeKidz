package com.example.materialeventcalendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.materialeventcalendar.EventItem
import com.example.materialeventcalendar.OnCalendarSwipeListener
import com.example.materialeventcalendar.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

//creating class for CalendarView using Linear Layout
class CalendarView @JvmOverloads
constructor(context: Context?, attrs: AttributeSet? =null, defStyleAttr: Int=0):
    LinearLayout(context, attrs, defStyleAttr){

    var MAX_EVENT = 3
    var eventClickListener: CalendarEventClickListener? = null
    companion object {
        const val myFormat = "MMM yyyy"
        const val dateFormat = "dd-MM-yyyy"
        const val datewithtimeformat = "dd-MM-yyyy hh:mm:ss"
        const val MARGIN = 15
        const val POST_TIME: Long = 100
    }
    private var eventList: ArrayList<EventItem> = ArrayList()
    private var calendar: Calendar = Calendar.getInstance()
    private var layoutInflater: LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var dayViewHeader: View = layoutInflater.inflate(R.layout.layout_day_header_view, this, false)
    private var dayViewRow1: FrameLayout = layoutInflater.inflate(R.layout.layout_day_date_view, this, false) as FrameLayout
    private var dayViewRow2: FrameLayout = layoutInflater.inflate(R.layout.layout_day_date_view, this, false) as FrameLayout
    private var dayViewRow3: FrameLayout = layoutInflater.inflate(R.layout.layout_day_date_view, this, false) as FrameLayout
    private var dayViewRow4: FrameLayout = layoutInflater.inflate(R.layout.layout_day_date_view, this, false) as FrameLayout
    private var dayViewRow5: FrameLayout = layoutInflater.inflate(R.layout.layout_day_date_view, this, false) as FrameLayout
    private var dayViewRow6: FrameLayout = layoutInflater.inflate(R.layout.layout_day_date_view, this, false) as FrameLayout

    private var monthTitle: TextView? = dayViewHeader.findViewById(R.id.text_month_title) as TextView
    private var btn_next: ImageView? = dayViewHeader.findViewById(R.id.btn_next) as ImageView
    private var btn_previous: ImageView? = dayViewHeader.findViewById(R.id.btn_previous) as ImageView

    init{
        removeAllViews()
        orientation = LinearLayout.VERTICAL
        addView(dayViewHeader)
        addView(dayViewRow1)
        addView(dayViewRow2)
        addView(dayViewRow3)
        addView(dayViewRow4)
        addView(dayViewRow5)
        addView(dayViewRow6)

        monthTitle?.text = SimpleDateFormat(myFormat, Locale.getDefault()).format(calendar.time)
        btn_next?.setOnClickListener { nextMonth() }
        btn_previous?.setOnClickListener { previousMonth() }
        setOnTouchListener(object : OnCalendarSwipeListener(context) {
            override fun onSwipeLeft() { nextMonth() }
            override fun onSwipeRight() { previousMonth() }
        })
        updateEventView(true)
    }
    fun addEvent(eventItem: EventItem, clearOldData: Boolean = false) {

        if (clearOldData) eventList.clear()

        (dayViewRow1.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow2.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow3.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow4.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow5.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow6.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        eventList.add(eventItem)
        val sortedList = eventList.sortedWith(compareBy { it.getStartDateToSort() })
        eventList.clear()
        eventList.addAll(sortedList)
        updateEventView()
    }

    fun addEventList(eventListItems: ArrayList<EventItem>, clearOldData: Boolean = false) {
        if (clearOldData) eventList.clear()
        (dayViewRow1.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow2.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow3.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow4.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow5.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow6.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()

        eventList.addAll(eventListItems)
        val sortedList = eventListItems.sortedWith(compareBy { it.getStartDateToSort() })
        eventList.clear()
        eventList.addAll(sortedList)
        updateEventView()
    }
    private fun updateEventView(firstTime: Boolean = false){
        var selectedCalendar: Calendar = Calendar.getInstance()
        selectedCalendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE))

        selectedCalendar.set(Calendar.DATE, 1)
        selectedCalendar.set(Calendar.HOUR, 0)
        selectedCalendar.set(Calendar.MINUTE, 0)
        selectedCalendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.DATE, 1)

        var totalDaysToShow: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val toFillInPreviousMonthDays = 1 - calendar.get(Calendar.DAY_OF_WEEK)
        calendar.set(Calendar.DATE, totalDaysToShow)

        val toFillInNextMonthDays = 7 - calendar.get(Calendar.DAY_OF_WEEK)
        totalDaysToShow += Math.abs(toFillInPreviousMonthDays) + toFillInNextMonthDays
        calendar.set(Calendar.DATE, 1)

        if(toFillInPreviousMonthDays != 0){
            selectedCalendar.add(Calendar.DAY_OF_YEAR, toFillInPreviousMonthDays)
        }
        val totalRows = totalDaysToShow.div(7)
        when(totalRows){
            4 -> {
                dayViewRow5.visibility = View.GONE
                dayViewRow6.visibility = View.GONE
            }
            5 -> {
                dayViewRow5.visibility = View.VISIBLE
                dayViewRow6.visibility = View.GONE
            }
            else -> {
                dayViewRow5.visibility = View.VISIBLE
                dayViewRow6.visibility = View.VISIBLE
            }
        }
        val dateFormatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        (dayViewRow1.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow2.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow3.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow4.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow5.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()
        (dayViewRow6.findViewById(R.id.layout_tripEvents) as LinearLayout).removeAllViews()

        for(i in 1..totalDaysToShow step 7){
            when(i){
                in 1..7 -> {
                    val layout_tripEvents = dayViewRow1.findViewById(R.id.layout_tripEvents) as LinearLayout
                    val minDate = selectedCalendar.time

                    val day1 = dayViewRow1.findViewById(R.id.day1) as TextView
                    day1.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)
                    var widthOfText = day1.measuredWidth

                    val day2 = dayViewRow1.findViewById(R.id.day2) as TextView
                    day2.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day3 = dayViewRow1.findViewById(R.id.day3) as TextView
                    day3.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day4 = dayViewRow1.findViewById(R.id.day4) as TextView
                    day4.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day5 = dayViewRow1.findViewById(R.id.day5) as TextView
                    day5.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day6 = dayViewRow1.findViewById(R.id.day6) as TextView
                    day6.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day7 = dayViewRow1.findViewById(R.id.day7) as TextView
                    day7.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val maxDate = selectedCalendar.time
                    if(!firstTime){
                        day1.postDelayed({
                            widthOfText = day1.measuredWidth
                            var eventAddCount = 0
                            for ( j in 0 until eventList.size){
                                val startDate = dateFormatter.parse(eventList.get(j).start)
                                val endDate = dateFormatter.parse(eventList.get(j).end)
                                if(eventAddCount == MAX_EVENT){break}

                                startDate.hours = 0
                                startDate.minutes = 0
                                startDate.seconds = 0
                                endDate.hours = 0
                                endDate.minutes = 0
                                endDate.seconds = 0

                                if(startDate > endDate){continue}
                                val eventTrip = layoutInflater.inflate(R.layout.layout_event_line, null) as CardView
                                val eventTitle = eventTrip.findViewById(R.id.txt_eventTitle) as TextView
                                eventTrip.setCardBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.setBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.text = eventList.get(j).title
                                eventTitle.setOnClickListener {
                                    eventClickListener?.onEventClick(eventList[j])
                                }

                                if(isDateInBetween(startDate, minDate, maxDate)){
                                    val daysBetween = if(isSameDay(minDate, startDate)) 0 else getDaysBetween(minDate, startDate) + 1
                                    val startMarginDays = (widthOfText * daysBetween) + MARGIN
                                    var endMarginDays = 0

                                    if(isDateInBetween(endDate, minDate, maxDate)){
                                        endMarginDays = (widthOfText * getDaysBetween(endDate, maxDate)) + MARGIN + widthOfText
                                    }
                                    val parameters = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                                    parameters.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, parameters)
                                    eventAddCount++
                                } else if (isDateInBetween(endDate, minDate, maxDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = (widthOfText * getDaysBetween(
                                        endDate,
                                        maxDate
                                    )) + MARGIN + widthOfText
                                    val params = LinearLayout.LayoutParams(
                                        LayoutParams.MATCH_PARENT,
                                        LayoutParams.WRAP_CONTENT
                                    )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }else if (isDateInBetween(minDate, startDate, maxDate) && isDateInBetween(maxDate, startDate, endDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = 0

                                    val params =
                                        LinearLayout.LayoutParams(
                                            LayoutParams.MATCH_PARENT,
                                            LayoutParams.WRAP_CONTENT
                                        )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }
                            }
                        }, POST_TIME)
                    }
                }
                in 8..14 -> {
                    val layout_tripEvents = dayViewRow2.findViewById(R.id.layout_tripEvents) as LinearLayout
                    val minDate = selectedCalendar.time

                    val day1 = dayViewRow2.findViewById(R.id.day1) as TextView
                    day1.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)
                    var widthOfText = day1.measuredWidth

                    val day2 = dayViewRow2.findViewById(R.id.day2) as TextView
                    day2.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day3 = dayViewRow2.findViewById(R.id.day3) as TextView
                    day3.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day4 = dayViewRow2.findViewById(R.id.day4) as TextView
                    day4.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day5 = dayViewRow2.findViewById(R.id.day5) as TextView
                    day5.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day6 = dayViewRow2.findViewById(R.id.day6) as TextView
                    day6.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day7 = dayViewRow2.findViewById(R.id.day7) as TextView
                    day7.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val maxDate = selectedCalendar.time
                    if(!firstTime){
                        day1.postDelayed({
                            widthOfText = day1.measuredWidth
                            var eventAddCount = 0
                            for ( j in 0 until eventList.size){
                                val startDate = dateFormatter.parse(eventList.get(j).start)
                                val endDate = dateFormatter.parse(eventList.get(j).end)
                                if(eventAddCount == MAX_EVENT){break}

                                startDate.hours = 0
                                startDate.minutes = 0
                                startDate.seconds = 0
                                endDate.hours = 0
                                endDate.minutes = 0
                                endDate.seconds = 0

                                if(startDate > endDate){continue}
                                val eventTrip = layoutInflater.inflate(R.layout.layout_event_line, null) as CardView
                                val eventTitle = eventTrip.findViewById(R.id.txt_eventTitle) as TextView
                                eventTrip.setCardBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.setBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.text = eventList.get(j).title
                                eventTitle.setOnClickListener {
                                    eventClickListener?.onEventClick(eventList[j])
                                }

                                if(isDateInBetween(startDate, minDate, maxDate)){
                                    val daysBetween = if(isSameDay(minDate, startDate)) 0 else getDaysBetween(minDate, startDate) + 1
                                    val startMarginDays = (widthOfText * daysBetween) + MARGIN
                                    var endMarginDays = 0

                                    if(isDateInBetween(endDate, minDate, maxDate)){
                                        endMarginDays = (widthOfText * getDaysBetween(endDate, maxDate)) + MARGIN + widthOfText
                                    }
                                    val parameters = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                                    parameters.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, parameters)
                                    eventAddCount++
                                } else if (isDateInBetween(endDate, minDate, maxDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = (widthOfText * getDaysBetween(
                                        endDate,
                                        maxDate
                                    )) + MARGIN + widthOfText
                                    val params = LinearLayout.LayoutParams(
                                        LayoutParams.MATCH_PARENT,
                                        LayoutParams.WRAP_CONTENT
                                    )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }else if (isDateInBetween(minDate, startDate, maxDate) && isDateInBetween(maxDate, startDate, endDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = 0

                                    val params =
                                        LinearLayout.LayoutParams(
                                            LayoutParams.MATCH_PARENT,
                                            LayoutParams.WRAP_CONTENT
                                        )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }
                            }
                        }, POST_TIME)
                    }
                }
                in 15..21 -> {
                    val layout_tripEvents = dayViewRow3.findViewById(R.id.layout_tripEvents) as LinearLayout
                    val minDate = selectedCalendar.time

                    val day1 = dayViewRow3.findViewById(R.id.day1) as TextView
                    day1.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)
                    var widthOfText = day1.measuredWidth

                    val day2 = dayViewRow3.findViewById(R.id.day2) as TextView
                    day2.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day3 = dayViewRow3.findViewById(R.id.day3) as TextView
                    day3.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day4 = dayViewRow3.findViewById(R.id.day4) as TextView
                    day4.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day5 = dayViewRow3.findViewById(R.id.day5) as TextView
                    day5.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day6 = dayViewRow3.findViewById(R.id.day6) as TextView
                    day6.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day7 = dayViewRow3.findViewById(R.id.day7) as TextView
                    day7.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val maxDate = selectedCalendar.time
                    if(!firstTime){
                        day1.postDelayed({
                            widthOfText = day1.measuredWidth
                            var eventAddCount = 0
                            for ( j in 0 until eventList.size){
                                val startDate = dateFormatter.parse(eventList.get(j).start)
                                val endDate = dateFormatter.parse(eventList.get(j).end)
                                if(eventAddCount == MAX_EVENT){break}

                                startDate.hours = 0
                                startDate.minutes = 0
                                startDate.seconds = 0
                                endDate.hours = 0
                                endDate.minutes = 0
                                endDate.seconds = 0

                                if(startDate > endDate){continue}
                                val eventTrip = layoutInflater.inflate(R.layout.layout_event_line, null) as CardView
                                val eventTitle = eventTrip.findViewById(R.id.txt_eventTitle) as TextView
                                eventTrip.setCardBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.setBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.text = eventList.get(j).title
                                eventTitle.setOnClickListener {
                                    eventClickListener?.onEventClick(eventList[j])
                                }

                                if(isDateInBetween(startDate, minDate, maxDate)){
                                    val daysBetween = if(isSameDay(minDate, startDate)) 0 else getDaysBetween(minDate, startDate) + 1
                                    val startMarginDays = (widthOfText * daysBetween) + MARGIN
                                    var endMarginDays = 0

                                    if(isDateInBetween(endDate, minDate, maxDate)){
                                        endMarginDays = (widthOfText * getDaysBetween(endDate, maxDate)) + MARGIN + widthOfText
                                    }
                                    val parameters = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                                    parameters.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, parameters)
                                    eventAddCount++
                                } else if (isDateInBetween(endDate, minDate, maxDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = (widthOfText * getDaysBetween(
                                        endDate,
                                        maxDate
                                    )) + MARGIN + widthOfText
                                    val params = LinearLayout.LayoutParams(
                                        LayoutParams.MATCH_PARENT,
                                        LayoutParams.WRAP_CONTENT
                                    )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }else if (isDateInBetween(minDate, startDate, maxDate) && isDateInBetween(maxDate, startDate, endDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = 0

                                    val params =
                                        LinearLayout.LayoutParams(
                                            LayoutParams.MATCH_PARENT,
                                            LayoutParams.WRAP_CONTENT
                                        )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }
                            }
                        }, POST_TIME)
                    }
                }
                in 22..28 -> {
                    val layout_tripEvents = dayViewRow4.findViewById(R.id.layout_tripEvents) as LinearLayout
                    val minDate = selectedCalendar.time

                    val day1 = dayViewRow4.findViewById(R.id.day1) as TextView
                    day1.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)
                    var widthOfText = day1.measuredWidth

                    val day2 = dayViewRow4.findViewById(R.id.day2) as TextView
                    day2.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day3 = dayViewRow4.findViewById(R.id.day3) as TextView
                    day3.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day4 = dayViewRow4.findViewById(R.id.day4) as TextView
                    day4.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day5 = dayViewRow4.findViewById(R.id.day5) as TextView
                    day5.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day6 = dayViewRow4.findViewById(R.id.day6) as TextView
                    day6.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day7 = dayViewRow4.findViewById(R.id.day7) as TextView
                    day7.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val maxDate = selectedCalendar.time
                    if(!firstTime){
                        day1.postDelayed({
                            widthOfText = day1.measuredWidth
                            var eventAddCount = 0
                            for ( j in 0 until eventList.size){
                                val startDate = dateFormatter.parse(eventList.get(j).start)
                                val endDate = dateFormatter.parse(eventList.get(j).end)
                                if(eventAddCount == MAX_EVENT){break}

                                startDate.hours = 0
                                startDate.minutes = 0
                                startDate.seconds = 0
                                endDate.hours = 0
                                endDate.minutes = 0
                                endDate.seconds = 0

                                if(startDate > endDate){continue}
                                val eventTrip = layoutInflater.inflate(R.layout.layout_event_line, null) as CardView
                                val eventTitle = eventTrip.findViewById(R.id.txt_eventTitle) as TextView
                                eventTrip.setCardBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.setBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.text = eventList.get(j).title
                                eventTitle.setOnClickListener {
                                    eventClickListener?.onEventClick(eventList[j])
                                }

                                if(isDateInBetween(startDate, minDate, maxDate)){
                                    val daysBetween = if(isSameDay(minDate, startDate)) 0 else getDaysBetween(minDate, startDate) + 1
                                    val startMarginDays = (widthOfText * daysBetween) + MARGIN
                                    var endMarginDays = 0

                                    if(isDateInBetween(endDate, minDate, maxDate)){
                                        endMarginDays = (widthOfText * getDaysBetween(endDate, maxDate)) + MARGIN + widthOfText
                                    }
                                    val parameters = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                                    parameters.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, parameters)
                                    eventAddCount++
                                } else if (isDateInBetween(endDate, minDate, maxDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = (widthOfText * getDaysBetween(
                                        endDate,
                                        maxDate
                                    )) + MARGIN + widthOfText
                                    val params = LinearLayout.LayoutParams(
                                        LayoutParams.MATCH_PARENT,
                                        LayoutParams.WRAP_CONTENT
                                    )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }else if (isDateInBetween(minDate, startDate, maxDate) && isDateInBetween(maxDate, startDate, endDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = 0

                                    val params =
                                        LinearLayout.LayoutParams(
                                            LayoutParams.MATCH_PARENT,
                                            LayoutParams.WRAP_CONTENT
                                        )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }
                            }
                        }, POST_TIME)
                    }
                }
                in 29..35 -> {
                    val layout_tripEvents = dayViewRow5.findViewById(R.id.layout_tripEvents) as LinearLayout
                    val minDate = selectedCalendar.time

                    val day1 = dayViewRow5.findViewById(R.id.day1) as TextView
                    day1.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)
                    var widthOfText = day1.measuredWidth

                    val day2 = dayViewRow5.findViewById(R.id.day2) as TextView
                    day2.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day3 = dayViewRow5.findViewById(R.id.day3) as TextView
                    day3.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day4 = dayViewRow5.findViewById(R.id.day4) as TextView
                    day4.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day5 = dayViewRow5.findViewById(R.id.day5) as TextView
                    day5.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day6 = dayViewRow5.findViewById(R.id.day6) as TextView
                    day6.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day7 = dayViewRow5.findViewById(R.id.day7) as TextView
                    day7.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val maxDate = selectedCalendar.time
                    if(!firstTime){
                        day1.postDelayed({
                            widthOfText = day1.measuredWidth
                            var eventAddCount = 0
                            for ( j in 0 until eventList.size){
                                val startDate = dateFormatter.parse(eventList.get(j).start)
                                val endDate = dateFormatter.parse(eventList.get(j).end)
                                if(eventAddCount == MAX_EVENT){break}

                                startDate.hours = 0
                                startDate.minutes = 0
                                startDate.seconds = 0
                                endDate.hours = 0
                                endDate.minutes = 0
                                endDate.seconds = 0

                                if(startDate > endDate){continue}
                                val eventTrip = layoutInflater.inflate(R.layout.layout_event_line, null) as CardView
                                val eventTitle = eventTrip.findViewById(R.id.txt_eventTitle) as TextView
                                eventTrip.setCardBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.setBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.text = eventList.get(j).title
                                eventTitle.setOnClickListener {
                                    eventClickListener?.onEventClick(eventList[j])
                                }

                                if(isDateInBetween(startDate, minDate, maxDate)){
                                    val daysBetween = if(isSameDay(minDate, startDate)) 0 else getDaysBetween(minDate, startDate) + 1
                                    val startMarginDays = (widthOfText * daysBetween) + MARGIN
                                    var endMarginDays = 0

                                    if(isDateInBetween(endDate, minDate, maxDate)){
                                        endMarginDays = (widthOfText * getDaysBetween(endDate, maxDate)) + MARGIN + widthOfText
                                    }
                                    val parameters = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                                    parameters.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, parameters)
                                    eventAddCount++
                                } else if (isDateInBetween(endDate, minDate, maxDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = (widthOfText * getDaysBetween(
                                        endDate,
                                        maxDate
                                    )) + MARGIN + widthOfText
                                    val params = LinearLayout.LayoutParams(
                                        LayoutParams.MATCH_PARENT,
                                        LayoutParams.WRAP_CONTENT
                                    )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }else if (isDateInBetween(minDate, startDate, maxDate) && isDateInBetween(maxDate, startDate, endDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = 0

                                    val params =
                                        LinearLayout.LayoutParams(
                                            LayoutParams.MATCH_PARENT,
                                            LayoutParams.WRAP_CONTENT
                                        )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }
                            }
                        }, POST_TIME)
                    }
                }
                in 36..42 -> {
                    val layout_tripEvents = dayViewRow6.findViewById(R.id.layout_tripEvents) as LinearLayout
                    val minDate = selectedCalendar.time

                    val day1 = dayViewRow6.findViewById(R.id.day1) as TextView
                    day1.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)
                    var widthOfText = day1.measuredWidth

                    val day2 = dayViewRow6.findViewById(R.id.day2) as TextView
                    day2.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day3 = dayViewRow6.findViewById(R.id.day3) as TextView
                    day3.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day4 = dayViewRow6.findViewById(R.id.day4) as TextView
                    day4.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day5 = dayViewRow6.findViewById(R.id.day5) as TextView
                    day5.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day6 = dayViewRow6.findViewById(R.id.day6) as TextView
                    day6.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val day7 = dayViewRow6.findViewById(R.id.day7) as TextView
                    day7.text = selectedCalendar.get(Calendar.DATE).toString()
                    selectedCalendar = addSingleDay(selectedCalendar)

                    val maxDate = selectedCalendar.time
                    if(!firstTime){
                        day1.postDelayed({
                            widthOfText = day1.measuredWidth
                            var eventAddCount = 0
                            for ( j in 0 until eventList.size){
                                val startDate = dateFormatter.parse(eventList.get(j).start)
                                val endDate = dateFormatter.parse(eventList.get(j).end)
                                if(eventAddCount == MAX_EVENT){break}

                                startDate.hours = 0
                                startDate.minutes = 0
                                startDate.seconds = 0
                                endDate.hours = 0
                                endDate.minutes = 0
                                endDate.seconds = 0

                                if(startDate > endDate){continue}
                                val eventTrip = layoutInflater.inflate(R.layout.layout_event_line, null) as CardView
                                val eventTitle = eventTrip.findViewById(R.id.txt_eventTitle) as TextView
                                eventTrip.setCardBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.setBackgroundColor(Color.parseColor(eventList.get(j).color))
                                eventTitle.text = eventList.get(j).title
                                eventTitle.setOnClickListener {
                                    eventClickListener?.onEventClick(eventList[j])
                                }

                                if(isDateInBetween(startDate, minDate, maxDate)){
                                    val daysBetween = if(isSameDay(minDate, startDate)) 0 else getDaysBetween(minDate, startDate) + 1
                                    val startMarginDays = (widthOfText * daysBetween) + MARGIN
                                    var endMarginDays = 0

                                    if(isDateInBetween(endDate, minDate, maxDate)){
                                        endMarginDays = (widthOfText * getDaysBetween(endDate, maxDate)) + MARGIN + widthOfText
                                    }
                                    val parameters = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                                    parameters.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, parameters)
                                    eventAddCount++
                                } else if (isDateInBetween(endDate, minDate, maxDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = (widthOfText * getDaysBetween(
                                        endDate,
                                        maxDate
                                    )) + MARGIN + widthOfText
                                    val params = LinearLayout.LayoutParams(
                                        LayoutParams.MATCH_PARENT,
                                        LayoutParams.WRAP_CONTENT
                                    )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }else if (isDateInBetween(minDate, startDate, maxDate) && isDateInBetween(maxDate, startDate, endDate)) {
                                    val startMarginDays = 0
                                    val endMarginDays = 0

                                    val params =
                                        LinearLayout.LayoutParams(
                                            LayoutParams.MATCH_PARENT,
                                            LayoutParams.WRAP_CONTENT
                                        )
                                    params.setMargins(startMarginDays, 5, endMarginDays, 5)
                                    layout_tripEvents.addView(eventTrip, params)
                                    eventAddCount++
                                }
                            }
                        }, POST_TIME)
                    }
                }
            }
        }
    }
    private fun setMonthTextColor(color: Int){
        (dayViewHeader.findViewById(R.id.text_month_title) as TextView).setTextColor(color)
    }
    private fun setWeekDayTitleTextColor(color: Int) {
        (dayViewHeader.findViewById(R.id.txt_sun) as TextView).setTextColor(color)
        (dayViewHeader.findViewById(R.id.txt_mon) as TextView).setTextColor(color)
        (dayViewHeader.findViewById(R.id.txt_tue) as TextView).setTextColor(color)
        (dayViewHeader.findViewById(R.id.txt_wed) as TextView).setTextColor(color)
        (dayViewHeader.findViewById(R.id.txt_thu) as TextView).setTextColor(color)
        (dayViewHeader.findViewById(R.id.txt_fri) as TextView).setTextColor(color)
        (dayViewHeader.findViewById(R.id.txt_sat) as TextView).setTextColor(color)
    }
    fun setHeaderColor(color: Int) {
        setMonthTextColor(color)
        setWeekDayTitleTextColor(color)
    }

    private fun addSingleDay(calendar: Calendar): Calendar {
        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

        val copyCalendar: Calendar = calendar.clone() as Calendar
        copyCalendar.set(Calendar.DATE, 1)

        val maxDaysInMonth = copyCalendar.getMaximum(Calendar.DAY_OF_MONTH)

        if (currentDayOfMonth == maxDaysInMonth) {
            if (currentDayOfYear == calendar.getActualMaximum(Calendar.DAY_OF_YEAR)) {
                calendar.add(Calendar.YEAR, 1)
                calendar.set(Calendar.MONTH, Calendar.JANUARY)
                calendar.set(Calendar.DAY_OF_YEAR, 1)
            } else {
                calendar.add(Calendar.MONTH, 1)
                calendar.set(Calendar.DATE, 1)
            }
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return calendar
    }
    fun nextMonth() {
        calendar.add(Calendar.MONTH, 1)
        monthTitle?.text = SimpleDateFormat(myFormat, Locale.getDefault()).format(calendar.time)
        updateEventView()
    }

    fun previousMonth() {
        calendar.add(Calendar.MONTH, -1)
        monthTitle?.text = SimpleDateFormat(myFormat, Locale.getDefault()).format(calendar.time)
        updateEventView()
    }

    private fun isDateInBetween(date: Date, minDate: Date, maxDate: Date): Boolean {
        date.hours = 0; date.minutes = 0; date.seconds = 0
        minDate.hours = 0; minDate.minutes = 0; minDate.seconds = 0
        maxDate.hours = 0; maxDate.minutes = 0; maxDate.seconds = 0
        if (isSameDay(date, minDate) || isSameDay(date, maxDate)) {
            return true
        }
        if (date.compareTo(minDate) >= 0 && date.compareTo(maxDate) <= 0) {
            return true
        }
        return false
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        calendar1.time = date1; calendar2.time = date2
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(
            Calendar.DAY_OF_YEAR
        )
    }

    private fun getDaysBetween(startDate: Date, endDate: Date): Int {
        startDate.hours = 0; startDate.minutes = 0; startDate.seconds = 0
        endDate.hours = 0; endDate.minutes = 0; endDate.seconds = 0

        val diff = endDate.time - startDate.time

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }
    fun setCalendarEventClickListener(eventClickListener: CalendarEventClickListener) {
        this.eventClickListener = eventClickListener
    }

    fun setMaxEventToShowPerWeek(maxEventCount: Int) {
        this.MAX_EVENT = maxEventCount
        updateEventView()
    }

    interface CalendarEventClickListener {
        fun onEventClick(eventItem: EventItem)
    }
}