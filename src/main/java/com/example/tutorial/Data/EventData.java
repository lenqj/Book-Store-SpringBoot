package com.example.tutorial.Data;

import com.example.tutorial.Model.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventData {
    private static final Map<Integer, Event> events = new HashMap<>();
    public static Event getByID(int ID){
        return events.get(ID);
    }
    public static void add(Event event){
        events.put(event.getID(), event);
    }
    public static void remove(int ID){
        events.remove(ID);
    }
    public static Collection<Event> getAll(){
        return events.values();
    }
}
