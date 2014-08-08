package uk.co.o2.android.helloroboguice.fragments;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.co.o2.android.helloroboguice.MyActivity;
import uk.co.o2.android.helloroboguice.R;
import uk.co.o2.android.helloroboguice.model.Friend;
import uk.co.o2.android.helloroboguice.model.Huddle;
import uk.co.o2.android.helloroboguice.ui.FriendsArrayAdapter;

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
        setListAdapter(new FriendsArrayAdapter(getActivity(), R.layout.friend_row, friendList));
        super.onViewCreated(view, savedInstanceState);
    }
}
