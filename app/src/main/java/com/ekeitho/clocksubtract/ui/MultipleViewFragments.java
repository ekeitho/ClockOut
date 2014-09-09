package com.ekeitho.clocksubtract.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Allow for multiple view in fragments.
 */

public class MultipleViewFragments extends FragmentPagerAdapter {


    public MultipleViewFragments(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new First_Clock_In();
            case 1:
                return new First_Clock_Out();
            case 2:
                return new Second_Clock_In();
            case 3:
                return new Fragment_Pick();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }


}
