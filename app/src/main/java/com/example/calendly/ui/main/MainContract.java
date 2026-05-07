package com.example.calendly.ui.main;

public interface MainContract {
    interface View {
        void showFragment(String tag);
    }

    interface Presenter {
        void onNavigationItemSelected(int itemId);
    }
}
