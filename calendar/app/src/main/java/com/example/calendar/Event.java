package com.example.calendar;

import androidx.annotation.NonNull;

public class Event {
    private String Title;
    private int Year, Month, Day, Hour, Minute;
    private String AM_PM;

    Event(String t, int day, int month, int year, int hour, int minute, String str){
        this.Title = t;
        this.Day = day;
        this.Month = month;
        this.Year = year;
        this.Hour = hour;
        this.Minute = minute;
        this.AM_PM = str;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public int getMinute() {
        return Minute;
    }

    public void setMinute(int minute) {
        Minute = minute;
    }

    public String getAM_PM() {
        return AM_PM;
    }

    public void setAM_PM(String AM_PM) {
        this.AM_PM = AM_PM;
    }
    public String returnDate(){
        StringBuilder sb = new StringBuilder();
        return sb.append("Date: " + this.Day + "/" + this.Month + "/" + this.Year +
                "\t\t" + this.Hour + ":" + this.Minute + "\t" + this.AM_PM).toString();
    }
}
