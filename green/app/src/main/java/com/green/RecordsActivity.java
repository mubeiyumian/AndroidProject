package com.green;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.green.adapter.ActivityRecordAdapter;
import com.green.dao.ActivityDao;
import com.green.dao.ParticipationActivityDao;
import com.green.pojo.Activity;
import com.green.pojo.ParticipationActivity;
import java.util.ArrayList;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {

    private ListView lvActivities;
    private TextView tvNoActivities;
    private ActivityRecordAdapter adapter;
    private List<Activity> activityList = new ArrayList<>();
    private String userId;
    private ParticipationActivityDao participationDao;
    private ActivityDao activityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
        participationDao = new ParticipationActivityDao(this);
        activityDao = new ActivityDao(this);

        initViews();
        loadParticipatedActivities();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        lvActivities = findViewById(R.id.lv_activities);
        tvNoActivities = findViewById(R.id.tv_no_activities);

        // 设置适配器
        adapter = new ActivityRecordAdapter(this, activityList);
        lvActivities.setAdapter(adapter);

        // 设置列表项点击事件
        lvActivities.setOnItemClickListener((parent, view, position, id) -> {
            Activity activity = activityList.get(position);
            openActivityDetail(activity);
        });
    }

    private void loadParticipatedActivities() {
        // 获取我参加的活动
        new Thread(() -> {
            List<Activity> tempList = new ArrayList<>();

            // 获取参与记录
            List<ParticipationActivity> participationList =
                    participationDao.getUserParticipations(userId);

            // 获取对应的活动
            for (ParticipationActivity participation : participationList) {
                Activity activity = activityDao.getActivityById(participation.getaId());
                if (activity != null) {
                    tempList.add(activity);
                }
            }

            runOnUiThread(() -> {
                activityList.clear();
                activityList.addAll(tempList);
                adapter.notifyDataSetChanged();
                updateEmptyView();
            });
        }).start();
    }

    private void updateEmptyView() {
        if (activityList.isEmpty()) {
            tvNoActivities.setVisibility(View.VISIBLE);
        } else {
            tvNoActivities.setVisibility(View.GONE);
        }
    }

    private void openActivityDetail(Activity activity) {
        Intent intent = new Intent(this, ActivityDetail.class);
        intent.putExtra("activity", activity);
        startActivity(intent);
    }
}