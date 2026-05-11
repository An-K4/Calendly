package com.example.calendly.ui.agenda;

import java.util.List;

public interface AgendaContract {
     interface View {
        void showAgenda(List<AgendaItem> items);
        void showEmptyState();
        void updateYearSelector(List<Integer> years, int currentYear);
    }

    interface Presenter {
         void loadYears();
         void loadAgendaByYear(int year);
         void onEventClicked(int eventId);
    }
}
