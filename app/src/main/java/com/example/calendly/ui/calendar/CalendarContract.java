package com.example.calendly.ui.calendar;

import com.example.calendly.data.model.Event;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;

public interface CalendarContract {
    interface View {
        void highlightDatesWithEvents(Set<LocalDate> dates);
        void showEventsOfSelectedDate(List<Event> events);
        void showEmptyState();
        void showError(String error);
    }

    interface Presenter {
        void onMonthChange(YearMonth month);
        void onDateSelected(LocalDate date);
        void onTodayClicked();
    }
}
