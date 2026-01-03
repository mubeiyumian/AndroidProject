package com.green.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.green.R;
import com.green.pojo.OnlineActivity;

import java.util.List;

public class PublicActivityAdapter extends BaseAdapter {
    private Context context;
    private List<OnlineActivity> activityList;

    public PublicActivityAdapter(Context context, List<OnlineActivity> activityList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_public_activity, parent, false);
            holder = new ViewHolder();
            holder.tvActivityName = convertView.findViewById(R.id.tv_activity_name);
            holder.tvDate = convertView.findViewById(R.id.tv_date);
            holder.tvRemainingSlots = convertView.findViewById(R.id.tv_remaining_slots);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OnlineActivity activity = activityList.get(position);
        holder.tvActivityName.setText(activity.getActivityName());
        holder.tvDate.setText("日期: " + formatDate(activity.getDate()));
        holder.tvRemainingSlots.setText("剩余名额: " + activity.getRemainingSlots());

        return convertView;
    }

    // 格式化日期显示
    private String formatDate(String dateStr) {
        if (dateStr != null && dateStr.length() == 8) {
            return dateStr.substring(0, 4) + "-" +
                    dateStr.substring(4, 6) + "-" +
                    dateStr.substring(6, 8);
        }
        return dateStr;
    }

    static class ViewHolder {
        TextView tvActivityName;
        TextView tvDate;
        TextView tvRemainingSlots;
    }
}
