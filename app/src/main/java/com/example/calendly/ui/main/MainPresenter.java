package com.example.calendly.ui.main;

import com.example.calendly.R;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onNavigationItemSelected(int itemId) {
        if (itemId == R.id.nav_calendar) {
            view.showFragment("CALENDAR");
        } else if (itemId == R.id.nav_day) {
            view.showFragment("DAY");
        } else if (itemId == R.id.nav_sources) {
            view.showFragment("SOURCES");
        }
    }
}
