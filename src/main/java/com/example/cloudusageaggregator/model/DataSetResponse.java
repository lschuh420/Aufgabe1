package com.example.cloudusageaggregator.model;

import java.util.List;

public class DataSetResponse {
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
