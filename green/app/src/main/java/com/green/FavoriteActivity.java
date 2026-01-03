package com.green;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.green.adapter.ActivityAdapter;
import com.green.dao.ActivityDao;
import com.green.dao.FavoriteDao;
import com.green.pojo.Activity;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private GridView gvActivities;
    private ActivityAdapter adapter;
    private ActivityDao activityDao;
    private FavoriteDao favoriteDao;
    private List<Activity> activityList = new ArrayList<>();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
        activityDao = new ActivityDao(this);
        favoriteDao = new FavoriteDao(this);

        initViews();
        loadFavoriteActivities();
    }

    private void initViews() {
        // 返回按钮
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        gvActivities = findViewById(R.id.gv_activities);

        // 设置点击事件
        gvActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = activityList.get(position);
                Intent intent = new Intent(FavoriteActivity.this, ActivityDetail.class);
                intent.putExtra("activity", activity);
                startActivity(intent);
            }
        });
    }

    private void loadFavoriteActivities() {
        // 获取用户收藏的活动ID列表
        List<String> favoriteActivityIds = favoriteDao.getFavoriteActivityIds(userId);

        // 根据ID列表获取活动详情
        for (String activityId : favoriteActivityIds) {
            Activity activity = activityDao.getActivityById(activityId);
            if (activity != null) {
                activityList.add(activity);
            }
        }

        // 设置适配器
        adapter = new ActivityAdapter(this, activityList);
        gvActivities.setAdapter(adapter);
    }
}