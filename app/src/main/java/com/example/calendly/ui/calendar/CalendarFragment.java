package com.example.calendly.ui.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.calendly.data.model.Event;
import com.example.calendly.data.repository.EventRepository;
import com.example.calendly.databinding.FragmentCalendarBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CalendarFragment extends Fragment implements CalendarContract.View {
    private FragmentCalendarBinding binding;
    private CalendarPresenter presenter;
    private EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CalendarPresenter(this, new EventRepository(requireContext()));

        setUpRecyclerView();
        setUpCalendarView();
    }

    private void setUpRecyclerView() {
        eventAdapter = new EventAdapter(new ArrayList<>(), event -> {
            // in development
        });
        binding.rvEventsOfDay.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvEventsOfDay.setAdapter(eventAdapter);
    }

    private void setUpCalendarView() {
        binding.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            LocalDate selectedDate = LocalDate.of(date.getYear(), date.getMonth() + 1, date.getDay());
            presenter.onDateSelected(selectedDate);
        });

        binding.calendarView.setOnMonthChangedListener((widget, date) -> {
            YearMonth month = YearMonth.of(date.getYear(), date.getMonth() + 1);
            presenter.onMonthChange(month);
        });

        binding.calendarView.setSelectedDate(CalendarDay.today());
        presenter.onMonthChange(YearMonth.now());
        presenter.onDateSelected(LocalDate.now());
    }

    @Override
    public void highlightDatesWithEvents(Set<LocalDate> dates) {
        List<CalendarDay> days = new ArrayList<>();
        for (LocalDate date: dates) {
            days.add(CalendarDay.from(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()));
        }

        binding.calendarView.removeDecorators();
        binding.calendarView.addDecorator(new EventDecorator(Color.RED, days));
    }

    @Override
    public void showEventsOfSelectedDate(List<Event> events) {
        binding.tvNoEvent.setVisibility(View.GONE);
        binding.rvEventsOfDay.setVisibility(View.VISIBLE);

        eventAdapter.updateData(events);
    }

    @Override
    public void showEmptyState() {
        binding.tvNoEvent.setVisibility(View.VISIBLE);
        binding.rvEventsOfDay.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }
}
