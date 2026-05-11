package com.example.calendly.data.repository;

import android.content.Context;

import com.example.calendly.data.db.AppDatabase;
import com.example.calendly.data.db.EventDao;
import com.example.calendly.data.model.Event;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    private EventDao eventDao;
    private ExecutorService executorService;

    public EventRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        eventDao = db.eventDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public interface DataCallBack<T> {
        void onDataLoaded(T data);
    }

    public void addEvent(Event event) {
        executorService.execute(() -> eventDao.insertEvent(event));
    }

    public void getEventsBetween(long start, long end, DataCallBack<List<Event>> callBack) {
        executorService.execute(() -> {
            List<Event> events = eventDao.getEventsBetween(start, end);
            callBack.onDataLoaded(events);
        });
    }

    public void getMaxEndTimeMillis(DataCallBack<Long> callBack) {
        executorService.execute(() -> {
            long endTimeMillis = eventDao.getMaxEndtimeMillis();
            callBack.onDataLoaded(endTimeMillis);
        });
    }

    public void deleteEvent(int eventId){
        executorService.execute(() -> eventDao.deleteEvent(eventId));
    }

    public void getAllEvents(DataCallBack<List<Event>> callBack) {
        executorService.execute(() -> {
            List<Event> events = eventDao.getAllEvents();
            callBack.onDataLoaded(events);
        });
    }
}
