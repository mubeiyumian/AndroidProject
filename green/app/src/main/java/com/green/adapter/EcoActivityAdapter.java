package com.green.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.green.R;
import com.green.pojo.Eco;
import java.util.List;

public class EcoActivityAdapter extends BaseAdapter {
    private Context context;
    private List<Eco> activityList;

    public EcoActivityAdapter(Context context, List<Eco> activityList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_eco_activity, parent, false);
            holder = new ViewHolder();
            holder.tvActivityName = convertView.findViewById(R.id.tv_activity_name);
            holder.tvActivityPoints = convertView.findViewById(R.id.tv_activity_points);
            holder.tvActivityTime = convertView.findViewById(R.id.tv_activity_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Eco activity = activityList.get(position);
        holder.tvActivityName.setText(activity.getEcoName());
        holder.tvActivityPoints.setText("+" + activity.getEcoPoints() + " 积分");

        // 格式化活动时间显示
        String timeDisplay;
        int timeInMinutes = activity.getEcoTime();
        if (timeInMinutes < 60) {
            timeDisplay = timeInMinutes + " 分钟";
        } else {
            int hours = timeInMinutes / 60;
            int minutes = timeInMinutes % 60;
            timeDisplay = hours + " 小时 " + minutes + " 分钟";
        }
        holder.tvActivityTime.setText(timeDisplay);

        return convertView;
    }

    static class ViewHolder {
        TextView tvActivityName;
        TextView tvActivityDate;
        TextView tvActivityPoints;
        TextView tvActivityTime;
    }
}
