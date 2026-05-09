package com.example.calendly.ui.event.add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calendly.data.repository.EventRepository;
import com.example.calendly.databinding.ActivityAddEventBinding;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddEventActivity extends AppCompatActivity implements AddEventContract.View {
    private ActivityAddEventBinding binding;
    private AddEventPresenter presenter;
    private LocalDate selectedStartDate;
    private LocalTime selectedStartTime;
    private LocalDate selectedEndDate;
    private LocalTime selectedEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEventBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int bottomHeight = Math.max(ime.bottom, systemBars.bottom);
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, bottomHeight);
            return insets;
        });

        presenter = new AddEventPresenter(this, new EventRepository(this));

        // set today time
        selectedStartDate = LocalDate.now();
        selectedStartTime = LocalTime.now();
        selectedEndDate = LocalDate.now();
        selectedEndTime = LocalTime.now().plusHours(1);
        binding.tvStartDayPicker.setText(selectedStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        binding.tvStartTimePicker.setText(selectedStartTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        binding.tvEndDayPicker.setText(selectedStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        binding.tvEndTimePicker.setText(selectedStartTime.plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm")));

        binding.checkboxAddDay.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                binding.tvStartTimePicker.setVisibility(View.GONE);
                binding.tvEndTimePicker.setVisibility(View.GONE);
            } else {
                binding.tvStartTimePicker.setVisibility(View.VISIBLE);
                binding.tvEndTimePicker.setVisibility(View.VISIBLE);
            }
        });
        binding.tvStartDayPicker.setOnClickListener(view -> {
            showDatePicker(selectedStartDate, true);
        });
        binding.tvStartTimePicker.setOnClickListener(view -> {
            showTimePicker(selectedStartTime, true);
        });
        binding.tvEndDayPicker.setOnClickListener(view -> {
            showDatePicker(selectedStartDate, false);
        });
        binding.tvEndTimePicker.setOnClickListener(view -> {
            showTimePicker(selectedStartTime, false);
        });

        binding.btnSaveEvent.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString();
            int startYear = selectedStartDate.getYear();
            int startMonth = selectedStartDate.getMonthValue();
            int startDay = selectedStartDate.getDayOfMonth();
            int startHour = selectedStartTime.getHour();
            int startMinute = selectedStartTime.getMinute();
            int endYear = selectedEndDate.getYear();
            int endMonth = selectedEndDate.getMonthValue();
            int endDay = selectedEndDate.getDayOfMonth();
            int endHour = selectedEndTime.getHour();
            int endMinute = selectedEndTime.getMinute();
            String description = binding.etDescription.getText().toString();
            String organizer = binding.etOrganizer.getText().toString();
            String location = binding.etLocation.getText().toString();
            boolean isAllDay = binding.checkboxAddDay.isChecked();

            presenter.saveEvent(
                    title,
                    startDay, startMonth, startYear,
                    startHour, startMinute,
                    endDay, endMonth, endYear,
                    endHour, endMinute,
                    description,
                    organizer,
                    location,
                    isAllDay
            );
        });
    }

    @Override
    public void onSaveSuccess() {
        Toast.makeText(this, "Đã lưu sự kiện", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDatePicker(LocalDate eventDay, boolean isStart) {
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    if (isStart) {
                        selectedStartDate = LocalDate.of(year, month + 1, dayOfMonth);
                        binding.tvStartDayPicker.setText(selectedStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    } else {
                        selectedEndDate = LocalDate.of(year, month + 1, dayOfMonth);
                        binding.tvEndDayPicker.setText(selectedEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                },
                eventDay.getYear(),
                eventDay.getMonthValue() - 1,
                eventDay.getDayOfMonth()
        );

        dialog.show();
    }

    @Override
    public void showTimePicker(LocalTime eventTime, boolean isStart) {
        eventTime = isStart ? eventTime : eventTime.plusHours(1);

        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, hour, minute) -> {
                    if (isStart){
                        selectedStartTime = LocalTime.of(hour, minute);
                        binding.tvStartTimePicker.setText(selectedStartTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                    } else {
                        selectedEndTime = LocalTime.of(hour, minute);
                        binding.tvEndTimePicker.setText(selectedEndTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                    }
                },
                eventTime.getHour(),
                eventTime.getMinute(),
                true
        );

        dialog.show();
    }
}