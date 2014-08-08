package uk.co.o2.android.helloroboguice.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.o2.android.helloroboguice.R;
import uk.co.o2.android.helloroboguice.model.Friend;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class FriendsArrayAdapter extends ArrayAdapter<Friend> {

    public FriendsArrayAdapter(Context context, int resource, List<Friend> friendList) {
        super(context, resource, friendList);
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
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.friend_row, parent);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Friend friend = getItem(position);
        viewHolder.checkBox.setChecked(friend.isSelected);
        viewHolder.profileImageView.setImageURI(Uri.parse(friend.image));
        viewHolder.likeTextView.setText("Test");
        viewHolder.nameTextView.setText(friend.name);
        viewHolder.chatIndicatorImageview.setImageResource(friend.primary == 1 ? R.drawable.chat_available : R.drawable.chat_unavailable);
        return convertView;
    }


    static class ViewHolder {
        CheckBox checkBox;
        TextView nameTextView;
        TextView likeTextView;
        ImageView profileImageView;
        ImageView chatIndicatorImageview;

        ViewHolder(View v) {
            checkBox = (CheckBox) v.findViewById(R.id.selected_CheckBox);
            nameTextView = (TextView) v.findViewById(R.id.name_TextView);
            likeTextView = (TextView) v.findViewById(R.id.like_TextView);
            profileImageView = (ImageView) v.findViewById(R.id.profile_ImageView);
            chatIndicatorImageview = (ImageView) v.findViewById(R.id.chatIndicator_ImageView);
        }
    }

}
