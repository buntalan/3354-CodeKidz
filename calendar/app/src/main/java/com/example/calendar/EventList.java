package com.example.calendar;

import com.example.calendar.Event;
import java.util.*;
import java.lang.StringBuilder;

public class EventList {
    ArrayList<Event> list;
    public EventList(){
        list = new ArrayList<Event>();
    }
    public EventList(ArrayList<Event> events){
        list = events;
    }
    public boolean addEvent(Event event){
        return list.add(event);
    }
    public boolean removeEvent(Event event){
        return list.remove(event);
    }
    @Override String toString(){
        StringBuilder sb = new StringBuilder();
        for (Event x: list){
            sb.append(Event.toString() + "\n");
        }
        return sb.toString();
    }
}