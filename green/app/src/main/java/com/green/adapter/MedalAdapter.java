package com.green.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.green.R;
import com.green.pojo.Medal;
import java.util.List;

public class MedalAdapter extends BaseAdapter {
    private Context context;
    private List<Medal> medalList;

    public MedalAdapter(Context context, List<Medal> medalList) {
        this.context = context;
        this.medalList = medalList;
    }

    @Override
    public int getCount() {
        return medalList.size();
    }

    @Override
    public Object getItem(int position) {
        return medalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_medal, parent, false);
            holder = new ViewHolder();
            holder.ivMedal = convertView.findViewById(R.id.iv_medal);
            holder.tvName = convertView.findViewById(R.id.tv_medal_name);
            holder.tvPoints = convertView.findViewById(R.id.tv_medal_points);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Medal medal = medalList.get(position);
        holder.tvName.setText(medal.getmName());
        holder.tvPoints.setText(medal.getmPoints() + " 积分");

        // 加载勋章图片
        int imgResId = context.getResources().getIdentifier(
                medal.getmImg(), "drawable", context.getPackageName());
        Glide.with(context).load(imgResId).into(holder.ivMedal);

        return convertView;
    }

    static class ViewHolder {
        ImageView ivMedal;
        TextView tvName;
        TextView tvPoints;
    }
}
