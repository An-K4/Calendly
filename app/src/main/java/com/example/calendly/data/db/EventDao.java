package com.example.calendly.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.calendly.data.model.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEvent(Event event);

    @Query("SELECT * FROM EVENTS WHERE startTimeMillis >= :start AND endTimeMillis <= :end")
    List<Event> getEventsBetween(long start, long end);

    @Query("DELETE FROM events WHERE id = :eventId")
    void deleteEvent(int eventId);

    @Query("SELECT * FROM events")
    List<Event> getAllEvents();
}
