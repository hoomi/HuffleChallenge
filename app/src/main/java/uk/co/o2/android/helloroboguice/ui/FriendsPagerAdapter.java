package uk.co.o2.android.helloroboguice.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import roboguice.RoboGuice;
import roboguice.inject.InjectResource;
import uk.co.o2.android.helloroboguice.MyActivity;
import uk.co.o2.android.helloroboguice.R;
import uk.co.o2.android.helloroboguice.fragments.AllFriendsFragment;
import uk.co.o2.android.helloroboguice.fragments.BaseFragment;
import uk.co.o2.android.helloroboguice.fragments.BaseListFragment;
import uk.co.o2.android.helloroboguice.fragments.OtherFriendsFragment;
import uk.co.o2.android.helloroboguice.fragments.RecommendedFragment;

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
