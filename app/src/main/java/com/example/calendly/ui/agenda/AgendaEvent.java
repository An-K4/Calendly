package com.example.calendly.ui.agenda;

import com.example.calendly.data.model.Event;

public class AgendaEvent {
    public final Event event;
    public final long startTimeDisplay;
    public final long endTimeDisplay;

    public AgendaEvent(Event event, long startTimeDisplay, long endTimeDisplay) {
        this.event = event;
        this.startTimeDisplay = startTimeDisplay;
        this.endTimeDisplay = endTimeDisplay;
    }
}
