package com.example.calendly.ui.agenda;

import android.os.Handler;
import android.os.Looper;

import com.example.calendly.data.model.Event;
import com.example.calendly.data.repository.EventRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AgendaPresenter implements AgendaContract.Presenter{
    private AgendaContract.View view;
    private EventRepository repository;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public AgendaPresenter(AgendaContract.View view, EventRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadYears() {
        int currentYear = LocalDate.now().getYear();
        ArrayList<Integer> years = new ArrayList<>();

        for (int i = Math.max(1970, currentYear - 10); i <= currentYear + 10; i++ ){
            years.add(i);
        }

        view.updateYearSelector(years, currentYear);
    }

    @Override
    public void loadAgendaByYear(int year) {
        long startTimeMillis = LocalDate
                .of(year, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        long endTimeMillis = LocalDate
                .of(year, 12, 31)
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        repository.getEventsBetween(startTimeMillis, endTimeMillis, events -> {
            List<AgendaItem> agendaItems = new ArrayList<>();
            LocalDate lastDate = null;

            for(Event event : events){
                LocalDate eventDate = Instant
                        .ofEpochMilli(event.startTimeMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (!eventDate.equals(lastDate)){
                    agendaItems.add(new AgendaItem(AgendaItem.TYPE_DATE_HEADER, eventDate));
                    lastDate = eventDate;
                }
                agendaItems.add(new AgendaItem(AgendaItem.TYPE_EVENT, eventDate));
            }

            mainHandler.post(() -> {
                if (agendaItems.isEmpty()) view.showEmptyState();
                else view.showAgenda(agendaItems);
            });
        });
    }

    @Override
    public void onEventClicked(int eventId) {
        // in development
    }
}
