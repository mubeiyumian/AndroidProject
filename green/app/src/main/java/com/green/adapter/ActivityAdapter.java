package com.green.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.green.R;
import com.green.pojo.Activity;
import java.util.List;

public class ActivityAdapter extends BaseAdapter {
    private Context context;
    private List<Activity> activityList;

    public ActivityAdapter(Context context, List<Activity> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    @Override
    public int getCount() {
        return activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
            holder = new ViewHolder();
            holder.ivActivity = convertView.findViewById(R.id.iv_activity);
            holder.tvActivityName = convertView.findViewById(R.id.tv_activity_name);
            holder.tvActivityDate = convertView.findViewById(R.id.tv_activity_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Activity activity = activityList.get(position);
        holder.tvActivityName.setText(activity.getaName());
        holder.tvActivityDate.setText(activity.getFormattedDate());

        // 设置活动图片（使用统一的背景图）
        holder.ivActivity.setImageResource(R.drawable.activity_bg);

        return convertView;
    }

    static class ViewHolder {
        ImageView ivActivity;
        TextView tvActivityName;
        TextView tvActivityDate;
    }
}
