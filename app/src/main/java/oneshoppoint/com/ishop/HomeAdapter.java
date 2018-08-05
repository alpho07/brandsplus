package oneshoppoint.com.ishop;

/**
 * Created by stephineosoro on 22/05/16.
 */

import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class HomeAdapter extends BaseExpandableListAdapter {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;
    public NetworkImageView thumbnail,Cat;
    public HomeAdapter(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

//    @Override
//    public Object getChildid(int groupPosition, int childPosition) {
//        return groups.get(groupPosition).children.get(childPosition);
//    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        final String id = (String) groups.get(groupPosition).id.get(childPosition);
        final String path = (String) groups.get(groupPosition).image.get(childPosition);

        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_details, null);
        }
        thumbnail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children);
        thumbnail.setImageUrl("https://www.oneshoppoint.com/images" + path, imageLoader);
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("The child id clicked is", id);
                Toast.makeText(activity, children + " and its id is " + id,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_group, null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        Cat.setImageUrl("https://www.oneshoppoint.com/images" + group.path, imageLoader);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}