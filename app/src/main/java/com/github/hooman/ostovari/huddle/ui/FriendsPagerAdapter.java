package com.github.hooman.ostovari.huddle.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import roboguice.RoboGuice;
import roboguice.inject.InjectResource;
import com.github.hooman.ostovari.huddle.MyActivity;
import com.github.hooman.ostovari.huddle.R;
import com.github.hooman.ostovari.huddle.fragments.AllFriendsFragment;
import com.github.hooman.ostovari.huddle.fragments.BaseListFragment;
import com.github.hooman.ostovari.huddle.fragments.OtherFriendsFragment;
import com.github.hooman.ostovari.huddle.fragments.RecommendedFragment;

/**
 * Created by hostova1 on 07/08/2014.
 */
public class FriendsPagerAdapter extends FragmentPagerAdapter {

    @InjectResource(R.array.tabs_name)
    private String[] tabNames;

    public FriendsPagerAdapter(MyActivity myActivity, FragmentManager fm) {
        super(fm);
        RoboGuice.getInjector(myActivity).injectMembers(this);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        try {
            switch (i) {
                case 0:
                    fragment = BaseListFragment.newInstance(RecommendedFragment.class, null);
                    break;
                case 1:
                    fragment = BaseListFragment.newInstance(OtherFriendsFragment.class, null);
                    break;
                default:
                    fragment = BaseListFragment.newInstance(AllFriendsFragment.class, null);
                    break;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
