package com.nongratis.timetracker.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nongratis.timetracker.fragments.TimeAnalysisFragment;
import com.nongratis.timetracker.fragments.TimerFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new TimeAnalysisFragment();
        }
        return new TimerFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}