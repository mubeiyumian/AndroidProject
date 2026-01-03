package com.green.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.green.R;
import com.green.pojo.Knowledge;

import java.util.List;

public class KnowledgeAdapter extends BaseAdapter {
    private Context context;
    private List<Knowledge> knowledgeList;

    public KnowledgeAdapter(Context context, List<Knowledge> knowledgeList) {
        this.context = context;
        this.knowledgeList = knowledgeList;
    }

    @Override
    public int getCount() {
        return knowledgeList.size();
    }

    @Override
    public Object getItem(int position) {
        return knowledgeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_knowledge, parent, false);
            holder = new ViewHolder();
            holder.tvKnowledgeTitle = convertView.findViewById(R.id.tv_knowledge_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Knowledge knowledge = knowledgeList.get(position);
        holder.tvKnowledgeTitle.setText(knowledge.getkName());

        return convertView;
    }

    static class ViewHolder {
        TextView tvKnowledgeTitle;
    }
}
