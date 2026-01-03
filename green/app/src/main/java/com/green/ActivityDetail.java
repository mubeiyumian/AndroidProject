package com.green;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.green.dao.FavoriteDao;
import com.green.dao.UserDao;
import com.green.pojo.Activity;
import com.green.dao.ActivityDao;
import com.green.dao.ParticipationActivityDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ActivityDetail extends AppCompatActivity {

    private Activity activity;
    private ImageView ivActivity;
    private TextView tvActivityName, tvActivityDate, tvActivityPlace, tvActivityContent;
    private Button btnJoinActivity;
    private Button btnAddFavorite, btnRemoveFavorite;
    private FavoriteDao favoriteDao;
    private UserDao userDao;

    private String userId;
    private int userPoints;
    private ParticipationActivityDao participationDao;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        favoriteDao = new FavoriteDao(this);
        userDao = new UserDao(this);


        // 获取当前用户ID
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = preferences.getString("userId", "0");
        userPoints = preferences.getInt("points", 0);
        participationDao = new ParticipationActivityDao(this);

        // 获取传递的活动数据
        activity = (Activity) getIntent().getSerializableExtra("activity");
        if (activity == null) {
            Toast.makeText(this, "活动数据获取失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupActivityData();
        checkUserFavorite();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        ivActivity = findViewById(R.id.iv_activity);
        tvActivityName = findViewById(R.id.tv_activity_name);
        tvActivityDate = findViewById(R.id.tv_activity_date);
        tvActivityPlace = findViewById(R.id.tv_activity_place);
        tvActivityContent = findViewById(R.id.tv_activity_content);
        btnJoinActivity = findViewById(R.id.btn_join_activity);

        btnAddFavorite = findViewById(R.id.btn_add_favorite);
        btnRemoveFavorite = findViewById(R.id.btn_remove_favorite);

        btnAddFavorite.setOnClickListener(v -> handleAddFavorite());
        btnRemoveFavorite.setOnClickListener(v -> handleRemoveFavorite());

        // 参加活动按钮点击事件
        btnJoinActivity.setOnClickListener(v -> handleJoinActivity());
    }

    private void handleAddFavorite() {
        String fId = UUID.randomUUID().toString();
        long result = favoriteDao.addFavorite(fId, activity.getaId(), userId);
        if (result != -1) {
            Toast.makeText(this, "已成功添加收藏！", Toast.LENGTH_SHORT).show();
            updateUIAfterAddFavorite();
        } else {
            Toast.makeText(this, "添加收藏失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleRemoveFavorite() {
        int rowsAffected = favoriteDao.removeFavorite(activity.getaId(), userId);
        if (rowsAffected > 0) {
            Toast.makeText(this, "已成功取消收藏！", Toast.LENGTH_SHORT).show();
            updateUIAfterRemoveFavorite();
        } else {
            Toast.makeText(this, "取消收藏失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUIAfterAddFavorite() {
        btnAddFavorite.setEnabled(false);
        btnRemoveFavorite.setEnabled(true);
    }

    private void updateUIAfterRemoveFavorite() {
        btnAddFavorite.setEnabled(true);
        btnRemoveFavorite.setEnabled(false);
    }

    private void checkUserFavorite() {
        boolean isFav = favoriteDao.isFavorite(activity.getaId(), userId);
        if (isFav) {
            updateUIAfterAddFavorite();
        } else {
            updateUIAfterRemoveFavorite();
        }
    }

    private void handleJoinActivity() {

        // 检查活动是否已过期
        if (isActivityExpired(activity.getaTime())) {
            Toast.makeText(this, "活动已过期，无法参加", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查用户是否已参加该活动
        if (participationDao.isUserParticipated(activity.getaId(), userId)) {
            Toast.makeText(this, "您已参加该活动", Toast.LENGTH_SHORT).show();
            return;
        }

        // 执行参加活动操作（包含人数检查）
        new Thread(() -> {
            // 检查活动人数限制
            int maxParticipants = getActivityMaxParticipants(activity.getaId());
            int currentParticipants = getActivityCurrentParticipants(activity.getaId());

            if (currentParticipants >= maxParticipants) {
                runOnUiThread(() ->
                        Toast.makeText(this, "活动人数已满，无法参加", Toast.LENGTH_SHORT).show()
                );
                return;
            }

            boolean success = joinActivityInDatabase();

            runOnUiThread(() -> {
                if (success) {
                    updateUIAfterJoin();
                    Toast.makeText(this, "已成功参加活动！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "参加活动失败，请重试", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private int getActivityMaxParticipants(String activityId) {
        ActivityDao activityDao = new ActivityDao(this);
        Activity activity = activityDao.getActivityById(activityId);
        return activity != null ? activity.getaNum() : 0;
    }

    private int getActivityCurrentParticipants(String activityId) {
        return participationDao.getParticipantCount(activityId);
    }

    private boolean joinActivityInDatabase() {
        // 插入参与记录
        long result = participationDao.addParticipation(activity.getaId(), userId);

        if (result != -1) {
            long res = userDao.updateUserPoints(userId, userPoints + 20);
            updateShared(userPoints + 20);
            Log.d("用户积分", "更新" + res);
            return true;
        }

        return false;
    }

    private void updateUIAfterJoin() {
        btnJoinActivity.setEnabled(false);
        btnJoinActivity.setText("已参加");
        btnJoinActivity.setBackgroundColor(getResources().getColor(R.color.gray));
    }

    private void checkUserParticipation() {
        if (userId != null && !userId.isEmpty()) {
            boolean hasParticipated = participationDao.isUserParticipated(activity.getaId(), userId);
            if (hasParticipated) {
                updateUIAfterJoin();
            }
        }
    }

    private boolean isActivityExpired(String activityDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = sdf.parse(activityDate);
            Date today = new Date();

            return date.before(today);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setupActivityData() {
        ivActivity.setImageResource(R.drawable.activity_bg);
        tvActivityName.setText(activity.getaName());
        tvActivityDate.setText("活动时间: " + activity.getFormattedDate());
        tvActivityPlace.setText("活动地点: " + activity.getaPlace());
        tvActivityContent.setText(activity.getaContent());
    }

    private void updateShared(int points) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("points", points);
        editor.apply();
    }
}