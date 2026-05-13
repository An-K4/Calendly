package com.example.calendly.ui.agenda;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.Objects;

public class AgendaItem {
    public static final int TYPE_DATE_HEADER = 0;
    public static final int TYPE_EVENT = 1;

    public int type;
    public LocalDate date;
    public AgendaEvent event;

    public AgendaItem(int type, LocalDate date) {
        this.type = type;
        this.date = date;
    }

    public AgendaItem(int type, AgendaEvent event) {
        this.type = type;
        this.event = event;
    }

    // auto scroll agenda way 1: override equals method for indexOf() - way 2 in AgendaFragment.java
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AgendaItem)) return false;
        AgendaItem other = (AgendaItem) obj;

        // use Objects.equals(date, other.date)
        // instead of date.equals(other.date) to avoid NullPointerException
        return type == other.type && Objects.equals(date, other.date) ;
    }
}
