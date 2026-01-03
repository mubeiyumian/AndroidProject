package com.green;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.green.adapter.EcoActivityAdapter;
import com.green.dao.EcoDao;
import com.green.pojo.Eco;
import java.util.List;

public class EcoListActivity extends AppCompatActivity {

    private ListView lvActivities;
    private TextView tvTotalPoints;
    private EcoDao ecoDao;
    private String userId;
    private List<Eco> activityList;
    private EcoActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_list);

        ecoDao = new EcoDao(this);
        userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");

        initViews();
        loadActivities();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        // 添加新活动按钮
        Button btnAddActivity = findViewById(R.id.btn_add_activity);
        btnAddActivity.setOnClickListener(v -> {
            Intent intent = new Intent(EcoListActivity.this, AddEcoActivity.class);
            startActivity(intent);
        });

        lvActivities = findViewById(R.id.lv_activities);
        tvTotalPoints = findViewById(R.id.tv_total_points);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadActivities();  // 返回时刷新数据
    }

    private void loadActivities() {
        // 获取用户活动记录
        activityList = ecoDao.getUserActivities(userId);

        // 计算总积分
        int totalPoints = 0;
        for (Eco activity : activityList) {
            totalPoints += activity.getEcoPoints();
        }
        tvTotalPoints.setText("总积分: " + totalPoints);

        // 设置适配器
        adapter = new EcoActivityAdapter(this, activityList);
        lvActivities.setAdapter(adapter);
    }
}