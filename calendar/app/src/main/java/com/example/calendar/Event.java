package com.example.calendar;

import java.util.Calendar;

public class Event {
    private String Title;
    private Calendar calendar;

    Event(String t, Calendar cal){
        this.Title = t;
        this.calendar = cal;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public Calendar getCalendar() {
        return calendar;
    }
}
