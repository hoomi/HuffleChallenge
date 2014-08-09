package com.github.hooman.ostovari.huddle.ui;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.hooman.ostovari.huddle.R;
import com.google.inject.Inject;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.model.ImageTagFactory;

import java.util.HashMap;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.event.EventManager;
import com.github.hooman.ostovari.huddle.events.FriendSelectedEvent;
import com.github.hooman.ostovari.huddle.model.Friend;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class FriendsArrayAdapter extends ArrayAdapter<Friend> implements CompoundButton.OnCheckedChangeListener {

    @Inject
    ImageManager imageManager;
    @Inject
    @ProfileLoader
    ImageTagFactory imageTagFactory;
    @Inject
    EventManager eventManager;

    private OnNumberOfSelectionChanged onNumberOfSelectionChanged;
    private static HashMap<String, Friend> selectedFriends;

    public FriendsArrayAdapter(Context context, int resource, List<Friend> friendList) {
        super(context, resource, friendList);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    public void setOnNumberOfSelectionChanged(OnNumberOfSelectionChanged onNumberOfSelectionChanged) {
        this.onNumberOfSelectionChanged = onNumberOfSelectionChanged;
    }

    public void setFriends(List<Friend> newFriends) {
        clear();
        if (newFriends != null && newFriends.size() > 0) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
                for (Friend f : newFriends) {
                    add(f);
                }
            } else {
                addAll(newFriends);
            }
        }
        if (selectedFriends != null) {
            selectedFriends.clear();
            if (onNumberOfSelectionChanged != null) {
                onNumberOfSelectionChanged.selectionChanged(0);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_row, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.checkBox.setOnCheckedChangeListener(this);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Friend friend = getItem(position);
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        viewHolder.checkBox.setChecked(friend.isSelected);
        viewHolder.checkBox.setTag(position);
        viewHolder.checkBox.setOnCheckedChangeListener(this);
        viewHolder.profileImageView.setTag(imageTagFactory.build(friend.image,getContext()));
        imageManager.getLoader().load(viewHolder.profileImageView);
        viewHolder.likeTextView.setText("Test");
        viewHolder.nameTextView.setText(friend.name);
        viewHolder.chatIndicatorImageView.setImageResource(friend.primary == 1 ? R.drawable.chat_available : R.drawable.chat_unavailable);
        return convertView;
    }

    private void selectFriend(Friend friend) {
        if (selectedFriends == null) {
            selectedFriends = new HashMap<String, Friend>();
        }
        selectedFriends.put(friend.id, friend);
    }

    private void removeFriend(Friend friend) {
        if (selectedFriends != null) {
            selectedFriends.remove(friend.id);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Integer position = (Integer) buttonView.getTag();
        if (position == null) {
            return;
        }
        Friend friend = getItem(position);
        friend.isSelected = isChecked;
        if (isChecked) {
            selectFriend(friend);
        } else {
            removeFriend(friend);
        }
        if (onNumberOfSelectionChanged != null) {
            onNumberOfSelectionChanged.selectionChanged(selectedFriends.size());
        }
        eventManager.fire(new FriendSelectedEvent());
    }

    public interface OnNumberOfSelectionChanged {
        public void selectionChanged(int selectedNumber);
    }


    static class ViewHolder {
        CheckBox checkBox;
        TextView nameTextView;
        TextView likeTextView;
        ImageView profileImageView;
        ImageView chatIndicatorImageView;

        ViewHolder(View v) {
            checkBox = (CheckBox) v.findViewById(R.id.selected_CheckBox);
            nameTextView = (TextView) v.findViewById(R.id.name_TextView);
            likeTextView = (TextView) v.findViewById(R.id.like_TextView);
            profileImageView = (ImageView) v.findViewById(R.id.profile_ImageView);
            chatIndicatorImageView = (ImageView) v.findViewById(R.id.chatIndicator_ImageView);
        }
    }

}
