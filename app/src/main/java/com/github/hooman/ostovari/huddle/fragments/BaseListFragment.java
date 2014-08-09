package com.github.hooman.ostovari.huddle.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;

import com.github.hooman.ostovari.huddle.R;
import com.google.inject.Inject;

import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.fragment.RoboListFragment;
import com.github.hooman.ostovari.huddle.events.FriendSelectedEvent;
import com.github.hooman.ostovari.huddle.events.HuddleRequestEvent;
import com.github.hooman.ostovari.huddle.model.Huddle;
import com.github.hooman.ostovari.huddle.ui.FriendsArrayAdapter;
import com.github.hooman.ostovari.huddle.utils.Logger;

/**
 * Created by hostova1 on 08/08/2014.
 */
public abstract class BaseListFragment<T extends RoboListFragment> extends RoboListFragment{
    @Inject
    protected EventManager eventManager;

    public static BaseListFragment newInstance(Class<? extends BaseListFragment> klass, Bundle arguments)
            throws java.lang.InstantiationException, IllegalAccessException {
        BaseListFragment fragment = klass.newInstance();
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setEmptyText(getString(R.string.empty_friends));
        super.onViewCreated(view, savedInstanceState);
    }

    protected void huddleUpdated(@Observes Huddle huddle) {
        Logger.d(this, "new Huddle arrived");
        setListShown(true);
    }

    protected void huddleRequested(@Observes HuddleRequestEvent huddleRequestEvent) {
        Logger.d(this, "new huddle is being requested");
        setListShown(false);
    }

    protected void friendSelected(@Observes FriendSelectedEvent friendSelectedEvent) {
        Logger.d(this, "Friend was selected");
        ListAdapter listAdapter = getListAdapter();
        if (listAdapter != null) {
            ((FriendsArrayAdapter)listAdapter).notifyDataSetChanged();
        }
    }
}
