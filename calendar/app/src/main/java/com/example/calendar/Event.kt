package com.example.calendar

import com.alamkanak.weekview.WeekViewDisplayable
import com.alamkanak.weekview.WeekViewEvent
import me.jlurena.revolvingweekview.WeekViewEvent
import java.util.*

data class Event(
    val id: Long,
    val title: String,
    val startTime: Calendar,
    val endTime: Calendar,
    val location: String,
    val color: Int,
    val isAllDay: Boolean,
    val isCanceled: Boolean
) : WeekViewDisplayable<Event> {

    override fun toWeekViewEvent(): WeekViewEvent<Event> {
        // Build the styling of the event, for instance background color and strike-through
        val style = WeekViewEvent.Style.Builder()
            .setBackgroundColor(color)
            .setTextStrikeThrough(isCanceled)
            .build()

        // Build the WeekViewEvent via the Builder
        return WeekViewEvent.Builder<Event>(this)
            .setId(id)
            .setTitle(title)
            .setStartTime(startTime)
            .setEndTime(endTime)
            .setLocation(location)
            .setAllDay(isAllDay)
            .setStyle(style)
            .build()
    }

    // TODO: Need to implement this
//    override fun toString(): String {
//        return super.toString()
//    }
}