package com.nongratis.timetracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.nongratis.timetracker.adapter.ViewPagerAdapter;
import com.nongratis.timetracker.utils.SyncScheduler;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new ViewPagerAdapter(this));
        viewPager2.setCurrentItem(1); // Set the TimerFragment as the initial fragment

        SyncScheduler.schedulePeriodicSync();
    }
}