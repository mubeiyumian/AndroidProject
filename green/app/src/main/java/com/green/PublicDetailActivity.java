package com.green;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.green.pojo.OnlineActivity;

public class PublicDetailActivity extends AppCompatActivity {
    private TextView tvActivityName, tvOrganization, tvDate, tvRemainingSlots, tvActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_detail);

        initViews();
        setupActivityData();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        tvActivityName = findViewById(R.id.tv_activity_name);
        tvOrganization = findViewById(R.id.tv_organization);
        tvDate = findViewById(R.id.tv_date);
        tvRemainingSlots = findViewById(R.id.tv_remaining_slots);
        tvActivityId = findViewById(R.id.tv_activity_id);
    }

    private void setupActivityData() {
        OnlineActivity activity = (OnlineActivity) getIntent().getSerializableExtra("activity");
        if (activity != null) {
            tvActivityName.setText("活动名称: " + activity.getActivityName());
            tvOrganization.setText("组织方: " + activity.getOrganization());
            tvDate.setText("日期: " + formatDate(activity.getDate()));
            tvRemainingSlots.setText("剩余名额: " + activity.getRemainingSlots());
            tvActivityId.setText("活动编号: " + activity.getActivityId());
        }
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
}