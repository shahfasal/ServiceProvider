package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.ComplaintsFragmentNew;
import fragments.ProfileFragment;

/**
 * Created by abhishek on 25-06-2015.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = null;

        if (pos == 0) {
            fragment = new ProfileFragment();
        }
        if (pos == 1) {
            fragment = new ComplaintsFragmentNew();
        }

        return fragment;
    }

    @Override
    public int getCount() {

        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "My Profile";
        }
        if (position == 1) {
            return "My Tasks";
        }
        return null;
    }
}