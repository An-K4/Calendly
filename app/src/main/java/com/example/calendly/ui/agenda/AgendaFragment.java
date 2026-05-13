package com.example.calendly.ui.agenda;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.calendly.data.repository.EventRepository;
import com.example.calendly.databinding.FragmentAgendaBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendaFragment extends Fragment implements AgendaContract.View {
    private AgendaPresenter presenter;
    private int currentYear;

    // _binding = null onDestroyView avoid memory leak
    // also can use _binding and binding() getter return _binding
    private FragmentAgendaBinding binding;
    private YearAdapter yearAdapter;
    private AgendaAdapter agendaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgendaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new AgendaPresenter(this, new EventRepository(this.getContext()));
        currentYear = LocalDate.now().getYear();

        yearAdapter = new YearAdapter(new ArrayList<>(), year -> {
            presenter.loadYears(year);
            presenter.loadAgendaByYear(year);
        });
        binding.rvYears.setLayoutManager(new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        ));
        binding.rvYears.setAdapter(yearAdapter);

        agendaAdapter = new AgendaAdapter(new ArrayList<>(), item -> {
            // in development
            Toast.makeText(getContext(), "In development", Toast.LENGTH_SHORT).show();
        });
        binding.rvAgenda.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvAgenda.setAdapter(agendaAdapter);

        presenter.loadYears(currentYear);
        presenter.loadAgendaByYear(currentYear);

        binding.btnPrevYear.setOnClickListener(v -> {
            currentYear -= 1;
            presenter.loadYears(currentYear);
            presenter.loadAgendaByYear(currentYear);
        });
        binding.btnPrevYear.setOnClickListener(v -> {
            currentYear += 1;
            presenter.loadYears(currentYear);
            presenter.loadAgendaByYear(currentYear);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void updateAgenda(List<AgendaItem> items) {
        binding.tvNoEvent.setVisibility(View.GONE);
        binding.rvAgenda.setVisibility(View.VISIBLE);

        AgendaItem today = new AgendaItem(AgendaItem.TYPE_DATE_HEADER, LocalDate.now());
        int todayPosition = items.indexOf(today);

        agendaAdapter.updateData(items);
        agendaAdapter.setSelectedPosition(todayPosition);
        binding.rvAgenda.scrollToPosition(todayPosition);

//        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.rvAgenda.getLayoutManager();
//        if (linearLayoutManager != null){
//            binding.rvAgenda.post(() -> linearLayoutManager.scrollToPosition(todayPosition));
//        }
    }

    @Override
    public void showEmptyState() {
        binding.tvNoEvent.setVisibility(View.VISIBLE);
        binding.rvAgenda.setVisibility(View.GONE);
    }

    @Override
    public void updateYearSelector(List<Integer> years, int currentYear) {
        if (currentYear < years.get(0)) {
            this.currentYear = years.get(0);
            return;
        }

        int newPosition = years.indexOf(currentYear);
        yearAdapter.updateData(years);
        yearAdapter.setSelectedPosition(newPosition);

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.rvYears.getLayoutManager();
        binding.rvYears.post(() -> {
            View firstChild = binding.rvYears.getChildAt(0);
            if (firstChild != null && linearLayoutManager != null) {
                int itemWidth = firstChild.getWidth();
                int offset = binding.rvYears.getWidth() / 2 - itemWidth / 2;
                linearLayoutManager.scrollToPositionWithOffset(newPosition, offset);
            }
        });

    }
}
