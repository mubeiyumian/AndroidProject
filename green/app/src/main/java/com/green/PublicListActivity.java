package com.green;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.green.adapter.PublicActivityAdapter;
import com.green.pojo.OnlineActivity;
import com.green.dao.OnlineActivityDao;

import java.util.ArrayList;
import java.util.List;

public class PublicListActivity extends AppCompatActivity {
    private ListView lvActivities;
    private PublicActivityAdapter adapter;
    private List<OnlineActivity> activityList = new ArrayList<>();
    private OnlineActivityDao activityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_list);

        activityDao = new OnlineActivityDao(this);

        initViews();
        loadActivityData();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        lvActivities = findViewById(R.id.lv_activities);
        adapter = new PublicActivityAdapter(this, activityList);
        lvActivities.setAdapter(adapter);

        // 设置列表项点击事件
        lvActivities.setOnItemClickListener((parent, view, position, id) -> {
            OnlineActivity activity = activityList.get(position);
            openActivityDetail(activity);
        });
    }

    private void loadActivityData() {
        new Thread(() -> {
            List<OnlineActivity> tempList = activityDao.getAllActivities();

            runOnUiThread(() -> {
                activityList.clear();
                activityList.addAll(tempList);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void openActivityDetail(OnlineActivity activity) {
        Intent intent = new Intent(this, PublicDetailActivity.class);
        intent.putExtra("activity", activity);
        startActivity(intent);
    }
}