package com.github.hooman.ostovari.huddle;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.event.EventManager;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import com.github.hooman.ostovari.huddle.events.HuddleRequestEvent;

import com.github.hooman.ostovari.huddle.model.Huddle;
import com.github.hooman.ostovari.huddle.servercommunication.FriendsAsyncLoader;
import com.github.hooman.ostovari.huddle.ui.FriendsArrayAdapter;
import com.github.hooman.ostovari.huddle.ui.FriendsPagerAdapter;
import com.github.hooman.ostovari.huddle.utils.Constants;


public class MyActivity extends RoboActionBarActivity implements LoaderManager.LoaderCallbacks<Huddle>{

    @InjectView (R.id.friends_ViewPager)
    private ViewPager friendsViewPage;
    @InjectResource (R.array.tabs_name)
    private String[] tabNames;

    @Inject
    protected EventManager eventManager;

    private Button createButton;
    private ImageButton backButton;

    private FriendsArrayAdapter.OnNumberOfSelectionChanged onNumberOfSelectionChanged = new FriendsArrayAdapter.OnNumberOfSelectionChanged() {
        @Override
        public void selectionChanged(int selectedNumber) {
            createButton.setEnabled(selectedNumber != 0);
        }
    };

    private Huddle globalHuddle;

    public Huddle getGlobalHuddle() {
        return globalHuddle;
    }

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
        getSupportLoaderManager().restartLoader(0,null,this).forceLoad();
    }

    private void initTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        View view = View.inflate(this,R.layout.actionbar_custom_layout,null);
        createButton = (Button) view.findViewById(R.id.create_Button);
        backButton = (ImageButton) view.findViewById(R.id.back_Button);
        actionBar.setCustomView(view,new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ActionBar.Tab tab;
        for (int i = 0 ; i < 3; i++) {
            tab = actionBar.newTab()
                    .setText(tabNames[i])
                    .setTabListener((new FriendTabListener()));
            actionBar.addTab(tab);
        }
    }

    public FriendsArrayAdapter.OnNumberOfSelectionChanged getOnNumberOfSelectionChanged() {
        return onNumberOfSelectionChanged;
    }

    @Override
    public Loader<Huddle> onCreateLoader(int i, Bundle bundle) {
        FriendsAsyncLoader friendsAsyncLoader = new FriendsAsyncLoader(this);
        friendsAsyncLoader.setServerUrl(Constants.SERVER_URL);
        friendsAsyncLoader.setItemId("27149164");
        eventManager.fire(new HuddleRequestEvent());
        return friendsAsyncLoader;
    }

    @Override
    public void onLoadFinished(Loader<Huddle> huddleLoader, Huddle huddle) {
        if (huddle != null) {
            globalHuddle = huddle;
            eventManager.fire(huddle);
        }

    }

    @Override
    public void onLoaderReset(Loader<Huddle> huddleLoader) {
        FriendsAsyncLoader friendsAsyncLoader = (FriendsAsyncLoader) huddleLoader;
        friendsAsyncLoader.setServerUrl(Constants.SERVER_URL);
        friendsAsyncLoader.setItemId("27149164");
        eventManager.fire(new HuddleRequestEvent());
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
