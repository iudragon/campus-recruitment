package com.projects.sharath.project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Sharath on 22-Feb-18.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:JobFragment jobFragment = new JobFragment();
                return jobFragment;


            case 1:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case 2:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "JOBS";
            case 1:
                return "FEED";
            case 2:
                return "PROFILE";
            default:
                return null;
        }
    }
}
