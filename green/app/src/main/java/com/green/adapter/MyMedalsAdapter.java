package com.green.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.green.R;
import com.green.pojo.Medal;
import java.util.List;
import java.util.Map;

public class MyMedalsAdapter extends BaseAdapter {
    private Context context;
    private List<Medal> medalList;
    private Map<String, Integer> userMedalsMap;

    public MyMedalsAdapter(Context context, List<Medal> medalList, Map<String, Integer> userMedalsMap) {
        this.context = context;
        this.medalList = medalList;
        this.userMedalsMap = userMedalsMap;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_medal, parent, false);
            holder = new ViewHolder();
            holder.ivMedal = convertView.findViewById(R.id.iv_medal);
            holder.tvName = convertView.findViewById(R.id.tv_medal_name);
            holder.tvPoints = convertView.findViewById(R.id.tv_medal_points);
            holder.tvCount = convertView.findViewById(R.id.tv_medal_count);
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

        // 检查用户是否拥有该勋章
        if (userMedalsMap.containsKey(medal.getmId())) {
            // 用户拥有该勋章，显示数量
            int count = userMedalsMap.get(medal.getmId());
            holder.tvCount.setText("已获得: " + count);
            holder.tvCount.setVisibility(View.VISIBLE);

            // 设置为正常状态
            holder.ivMedal.setAlpha(1.0f);
            holder.tvName.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            holder.tvPoints.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            // 用户未获得该勋章，置为灰色
            holder.tvCount.setVisibility(View.GONE);

            // 设置为灰色状态
            holder.ivMedal.setAlpha(0.3f);
            holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.gray));
            holder.tvPoints.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView ivMedal;
        TextView tvName;
        TextView tvPoints;
        TextView tvCount;
    }
}
