package com.example.calendly.ui.event.add;

import android.util.Log;

import com.example.calendly.data.model.Event;
import com.example.calendly.data.repository.EventRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class AddEventPresenter implements AddEventContract.Presenter {
    private AddEventContract.View view;
    private EventRepository repository;

    public AddEventPresenter(AddEventContract.View view, EventRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void saveEvent(
            String title,
            int startDay, int startMonth, int startYear,
            int startHour, int startMinute,
            int endDay, int endMonth, int endYear,
            int endHour, int endMinute,
            String description,
            String organizer,
            String location,
            Long reminderMinutes,
            boolean isAllDay
    ) {
        if (title == null || title.trim().isEmpty()) {
            view.showError("Tên sự kiện không được để trống");
            return;
        }

        try {
            LocalDateTime start;
            LocalDateTime end;

            if (isAllDay) {
                start = LocalDateTime.of(startYear, startMonth, startDay, 0, 0);
                end = LocalDateTime.of(endYear, endMonth, endDay, 23, 59);
            } else {
                start = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute);
                end = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMinute);
            }

            if (start.isAfter(end) || start.isEqual(end)) {
                view.showError("Thời gian bắt đầu không được muộn hơn thời gian kết thúc");
                return;
            }

            long startTimeMillis = start
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();
            long endTimeMillis = end
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();

            Event newEvent = new Event();
            newEvent.uuid = UUID.randomUUID().toString();
            newEvent.title = title.trim();
            newEvent.startTimeMillis = startTimeMillis;
            newEvent.endTimeMillis = endTimeMillis;
            newEvent.description = description == null ? "" : description.trim();
            newEvent.organizer = organizer == null ? "" : organizer.trim();
            newEvent.location = location == null ? "" : location.trim();
            newEvent.reminderMinutes = reminderMinutes;
            newEvent.sourceId = 0;
            newEvent.isAllDay = isAllDay;

            repository.addEvent(newEvent);
            view.onSaveSuccess();
        } catch (Exception e) {
            Log.e("Lỗi thêm sự kiện: ", e.getMessage() == null ? "không xác định" : e.getMessage());
            view.showError("Lỗi khi thêm sự kiện, hãy thử lại");
        }
    }
}
