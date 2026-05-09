package com.example.calendly.ui.event.add;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AddEventContract {
    interface View {
        void onSaveSuccess();

        void showError(String message);

        void showDatePicker(LocalDate eventDay, boolean isStart);

        void showTimePicker(LocalTime eventTime, boolean isStart);
    }

    interface Presenter {
        void saveEvent(
                String title,
                int startDay, int startMonth, int startYear,
                int startHour, int startMinute,
                int endDay, int endMonth, int endYear,
                int endHour, int endMinute,
                String description,
                String organizer,
                String location,
                boolean isAllDay
        );
    }
}
