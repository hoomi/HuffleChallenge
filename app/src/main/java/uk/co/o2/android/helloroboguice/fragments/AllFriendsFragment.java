package uk.co.o2.android.helloroboguice.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import roboguice.event.Observes;
import uk.co.o2.android.helloroboguice.MyActivity;
import uk.co.o2.android.helloroboguice.R;
import uk.co.o2.android.helloroboguice.model.Friend;
import uk.co.o2.android.helloroboguice.model.Huddle;
import uk.co.o2.android.helloroboguice.ui.FriendsArrayAdapter;
import uk.co.o2.android.helloroboguice.utils.Logger;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AllFriendsFragment extends BaseListFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Huddle huddle = ((MyActivity) getActivity()).getGlobalHuddle();
        List<Friend> friendList;
        if (huddle == null || huddle.all == null) {
            friendList = new ArrayList<Friend>();
        } else {
            friendList = huddle.all;
        }
        FriendsArrayAdapter friendsArrayAdapter = new FriendsArrayAdapter(getActivity(), R.layout.friend_row, friendList);
        friendsArrayAdapter.setOnNumberOfSelectionChanged(((MyActivity)getActivity()).getOnNumberOfSelectionChanged());
        setListAdapter(friendsArrayAdapter);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void huddleUpdated(@Observes Huddle huddle) {
        if (huddle != null) {
            ((FriendsArrayAdapter)getListAdapter()).setFriends(huddle.all);
        }
        super.huddleUpdated(huddle);
    }
}
