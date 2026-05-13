package com.example.calendly.ui.agenda;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.calendly.data.model.Event;
import com.example.calendly.data.repository.EventRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AgendaPresenter implements AgendaContract.Presenter {
    private AgendaContract.View view;
    private EventRepository repository;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public AgendaPresenter(AgendaContract.View view, EventRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadYears(int currentYear) {
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = Math.max(1970, currentYear - 10); i <= currentYear + 10; i++) {
            years.add(i);
        }

        Log.d("current year", String.valueOf(currentYear));
        view.updateYearSelector(years, currentYear);
    }

    @Override
    public void loadAgendaByYear(int year) {
        long startYearMillis = LocalDate
                .of(year, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        long endYearMillis = LocalDate
                .of(year, 12, 31)
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        repository.getEventsBetween(startYearMillis, endYearMillis, events -> {
            // use TreeMap to save multi day events for rendering, wrapper AgendaEvent to custom start and end time
            TreeMap<LocalDate, List<AgendaEvent>> eventsByDate = new TreeMap<>();

            for (Event event : events) {
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDate startDate = Instant.ofEpochMilli(event.startTimeMillis).atZone(zoneId).toLocalDate();
                LocalDate endDate = Instant.ofEpochMilli(event.endTimeMillis).atZone(zoneId).toLocalDate();

                boolean isOneDayEvent = startDate.isEqual(endDate);
                LocalDate cursor = startDate;
                while (!cursor.isAfter(endDate)) {
                    long startTimeDisplay;
                    long endTimeDisplay;

                    if (isOneDayEvent) {
                        // one day event - keep unchanged
                        startTimeDisplay = event.startTimeMillis;
                        endTimeDisplay = event.endTimeMillis;
                    } else if (cursor.isEqual(startDate)) {
                        // multi day event, start day - keep start time, end time 23:59
                        startTimeDisplay = event.startTimeMillis;
                        endTimeDisplay = cursor.atTime(23, 59).atZone(zoneId).toInstant().toEpochMilli();
                    } else if (cursor.equals(endDate)) {
                        // multi day event, end day - keep end time, start time 00:00
                        startTimeDisplay = cursor.atStartOfDay(zoneId).toInstant().toEpochMilli();
                        endTimeDisplay = event.endTimeMillis;
                    } else {
                        // multi day event, middle day - start time 00:00, end time 23:59
                        startTimeDisplay = cursor.atStartOfDay(zoneId).toInstant().toEpochMilli();
                        endTimeDisplay = cursor.atTime(23, 59).atZone(zoneId).toInstant().toEpochMilli();
                    }

                    eventsByDate
                            .computeIfAbsent(cursor, d -> new ArrayList<>())
                            .add(new AgendaEvent(event, startTimeDisplay, endTimeDisplay));

                    // plus one day to continue check loop
                    // before: cursor.plusDays(1); - forget to reassign -> crash because of infinite loop
                    // after:
                    cursor = cursor.plusDays(1);
                }
            }

            List<AgendaItem> agendaItems = new ArrayList<>();
            for (Map.Entry<LocalDate, List<AgendaEvent>> entry : eventsByDate.entrySet()) {
                agendaItems.add(new AgendaItem(AgendaItem.TYPE_DATE_HEADER, entry.getKey()));
                for (AgendaEvent event : entry.getValue()) {
                    agendaItems.add(new AgendaItem(AgendaItem.TYPE_EVENT, event));
                }
            }

            mainHandler.post(() -> {
                if (agendaItems.isEmpty()) view.showEmptyState();
                else view.updateAgenda(agendaItems);
            });
        });
    }

    @Override
    public void onEventClicked(int eventId) {
        // in development
    }
}
