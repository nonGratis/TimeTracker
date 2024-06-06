package com.nongratis.timetracker.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.nongratis.timetracker.R;
import com.nongratis.timetracker.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);

        viewPager2.setAdapter(new ViewPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.FIRST_PAGE_NAME);
                    tab.setIcon(R.drawable.ic_timer);
                    break;
                case 1:
                    tab.setText(R.string.SECOND_PAGE_NAME);
                    tab.setIcon(R.drawable.ic_analysis);
                    break;
            }
        }).attach();
    }
}