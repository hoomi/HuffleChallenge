package com.github.hooman.ostovari.huddle.fragments;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import roboguice.event.Observes;
import com.github.hooman.ostovari.huddle.MyActivity;
import com.github.hooman.ostovari.huddle.R;
import com.github.hooman.ostovari.huddle.model.Friend;
import com.github.hooman.ostovari.huddle.model.Huddle;
import com.github.hooman.ostovari.huddle.ui.FriendsArrayAdapter;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class OtherFriendsFragment extends BaseListFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Huddle huddle = ((MyActivity) getActivity()).getGlobalHuddle();
        List<Friend> friendList;
        if (huddle == null || huddle.other == null) {
            friendList = new ArrayList<Friend>();
        } else {
            friendList = huddle.other;
        }
        FriendsArrayAdapter friendsArrayAdapter = new FriendsArrayAdapter(getActivity(), R.layout.friend_row, friendList);
        friendsArrayAdapter.setOnNumberOfSelectionChanged(((MyActivity)getActivity()).getOnNumberOfSelectionChanged());
        setListAdapter(friendsArrayAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void huddleUpdated(@Observes Huddle huddle) {
        if (huddle != null) {
            ((FriendsArrayAdapter)getListAdapter()).setFriends(huddle.other);
        }
        super.huddleUpdated(huddle);
    }
}
