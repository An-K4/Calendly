package com.example.calendly.ui.calendar;

import android.os.Handler;
import android.os.Looper;

import com.example.calendly.data.model.Event;
import com.example.calendly.data.repository.EventRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

public class CalendarPresenter implements CalendarContract.Presenter{
    private CalendarContract.View view;
    private EventRepository repository;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public CalendarPresenter(CalendarContract.View view, EventRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onMonthChange(YearMonth month) {
        ZoneId zoneId = ZoneId.systemDefault();

        long start = month.atDay(1).atStartOfDay(zoneId).toInstant().toEpochMilli();
        long end = month.atEndOfMonth().atTime(23, 59, 59).atZone(zoneId).toInstant().toEpochMilli();

        repository.getEventsIntersecting(start, end, events -> {
            Set<LocalDate> eventDates = new HashSet<>();
            for (Event event: events) {
                LocalDate startDate = Instant.ofEpochMilli(event.startTimeMillis).atZone(zoneId).toLocalDate();
                LocalDate endDate = Instant.ofEpochMilli(event.endTimeMillis).atZone(zoneId).toLocalDate();

                LocalDate cursor = startDate;
                while (!cursor.isAfter(endDate)){
                    if (cursor.getMonth() == month.getMonth() && cursor.getYear() == month.getYear()) eventDates.add(cursor);

                    cursor = cursor.plusDays(1);
                }
            }

            mainHandler.post(() -> view.highlightDatesWithEvents(eventDates));
        });
    }

    @Override
    public void onDateSelected(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        long start = date.atStartOfDay(zoneId).toInstant().toEpochMilli();
        long end = date.atTime(23, 59, 59).atZone(zoneId).toInstant().toEpochMilli();

        repository.getEventsIntersecting(start, end, events -> {
            for (Event event: events) {
                LocalDate startDate = Instant.ofEpochMilli(event.startTimeMillis).atZone(zoneId).toLocalDate();
                LocalDate endDate = Instant.ofEpochMilli(event.endTimeMillis).atZone(zoneId).toLocalDate();

                if (!startDate.isEqual(date)){
                    // multi day event, start date is not selected date -> startTimeMillis is 00:00:00
                    event.startTimeMillis = date.atStartOfDay().atZone(zoneId).toInstant().toEpochMilli();
                }

                if (!endDate.isEqual(date)){
                    // multi day event, start date is not selected date -> endTimeMillis is 23:59:59
                    event.endTimeMillis = date.atTime(23, 59, 59).atZone(zoneId).toInstant().toEpochMilli();
                }
            }

            mainHandler.post(() -> {
                if (events.isEmpty()) view.showEmptyState();
                else view.showEventsOfSelectedDate(events);
            });
        });
    }

    @Override
    public void onTodayClicked() {

    }
}
