package com.example.calendly.ui.agenda;

import java.util.List;

public interface AgendaContract {
     interface View {
        void updateAgenda(List<AgendaItem> items);
        void showEmptyState();
        void updateYearSelector(List<Integer> years, int currentYear);
    }

    interface Presenter {
         void loadYears(int currentYear);
         void loadAgendaByYear(int year);
         void onEventClicked(int eventId);
    }
}
