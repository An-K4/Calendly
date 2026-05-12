package com.example.calendly.ui.agenda;

import com.example.calendly.data.model.Event;

import java.time.LocalDate;

public class AgendaItem {
    public static final int TYPE_DATE_HEADER = 0;
    public static final int TYPE_EVENT = 1;

    public int type;
    public LocalDate date;
    public Event event;

    public AgendaItem(int type, LocalDate date) {
        this.type = type;
        this.date = date;
    }

    public AgendaItem(int type, Event event) {
        this.type = type;
        this.event = event;
    }
}
