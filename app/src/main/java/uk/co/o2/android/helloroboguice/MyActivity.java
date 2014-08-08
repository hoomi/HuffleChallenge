package uk.co.o2.android.helloroboguice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import uk.co.o2.android.helloroboguice.fragments.AllFriendsFragment;
import uk.co.o2.android.helloroboguice.fragments.BaseFragment;
import uk.co.o2.android.helloroboguice.fragments.RecommendedFragment;
import uk.co.o2.android.helloroboguice.ui.FriendsPagerAdapter;


public class MyActivity extends RoboActionBarActivity {

    @InjectView (R.id.friends_ViewPager)
    ViewPager friendsViewPage;
    @InjectResource (R.array.tabs_name)
    private String[] tabNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        friendsViewPage.setAdapter(new FriendsPagerAdapter(this, getSupportFragmentManager()));
        friendsViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                getSupportActionBar().setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        initTabs();
    }

    private void initTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        ActionBar.Tab tab;
        for (int i = 0 ; i < 3; i++) {
            tab = actionBar.newTab()
                    .setText(tabNames[i])
                    .setTabListener((new FriendTabListener()));
            actionBar.addTab(tab);
        }
    }

    public class FriendTabListener implements ActionBar.TabListener {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    friendsViewPage.setCurrentItem(tab.getPosition(),true);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

    }
}
