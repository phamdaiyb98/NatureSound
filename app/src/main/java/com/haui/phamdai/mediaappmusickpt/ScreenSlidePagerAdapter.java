package com.haui.phamdai.mediaappmusickpt;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    public ScreenSlidePagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new PlayerFragment();
        }
        return new SongFragment();
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
