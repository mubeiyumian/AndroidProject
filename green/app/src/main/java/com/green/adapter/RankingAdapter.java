package com.green.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.green.R;
import com.green.pojo.User;
import java.util.List;

public class RankingAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;

    public RankingAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ranking, parent, false);
            holder = new ViewHolder();
            holder.tvRank = convertView.findViewById(R.id.tv_rank);
            holder.tvUsername = convertView.findViewById(R.id.tv_username);
            holder.tvPoints = convertView.findViewById(R.id.tv_points);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = userList.get(position);
        holder.tvRank.setText(String.valueOf(position + 1));  // 显示排名序号
        holder.tvUsername.setText(user.getUsername());
        holder.tvPoints.setText(user.getPoints() + " 积分");

        holder.tvRank.setBackgroundResource(android.R.color.transparent);
        holder.tvRank.setTextColor(context.getResources().getColor(android.R.color.black));

        return convertView;
    }

    static class ViewHolder {
        TextView tvRank;
        TextView tvUsername;
        TextView tvPoints;
    }
}
