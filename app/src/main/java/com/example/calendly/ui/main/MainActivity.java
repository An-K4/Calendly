package com.example.calendly.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.calendly.R;
import com.example.calendly.databinding.ActivityMainBinding;
import com.example.calendly.ui.calendar.CalendarFragment;
import com.example.calendly.ui.day.DayViewFragment;
import com.example.calendly.ui.sources.SourceManagerFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActivityMainBinding binding;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        presenter = new MainPresenter(this);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            presenter.onNavigationItemSelected(item.getItemId());
            return true;
        });

        if (savedInstanceState == null){
            binding.bottomNavigation.setSelectedItemId(R.id.nav_calendar);
        }
    }

    @Override
    public void showFragment(String tag) {
        Fragment fragment;

        switch (tag){
            case "CALENDAR":
                 fragment = new CalendarFragment();
                break;
            case "DAY":
                 fragment = new DayViewFragment();
                break;
            case "SOURCES":
                 fragment = new SourceManagerFragment();
                break;
            default:
                return;
        }

        // fragment switch
        // shouldn't user addToBackStack here, user want to click back -> go to main tab or exit app
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}