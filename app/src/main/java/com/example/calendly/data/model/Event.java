package com.example.calendly.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String uuid;
    public String title;
    public long startTimeMillis;
    public long endTimeMillis;
    public String location;
    public String organizer;
    public int sourceId;
    public boolean isAllDay;
}
