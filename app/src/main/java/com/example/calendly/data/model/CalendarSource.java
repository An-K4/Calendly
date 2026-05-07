package com.example.calendly.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "calendar_sources")
public class CalendarSource {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String displayName;
    public String filePath;
    public String color;
    public Boolean isVisible;
}
