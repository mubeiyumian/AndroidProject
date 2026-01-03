// PointsExchangeActivity.java (积分兑换页面)
package com.green;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.green.adapter.MedalAdapter;
import com.green.adapter.RankingAdapter;
import com.green.dao.MedalDao;
import com.green.pojo.RecordMedal;
import com.green.dao.RecordMedalDao;
import com.green.pojo.User;
import com.green.dao.UserDao;
import com.green.pojo.Medal;
import java.util.List;

public class PointsExchangeActivity extends AppCompatActivity {

    private TextView tvCurrentPoints;
    private GridView gvMedals;
    private UserDao userDao;
    private MedalDao medalDao;
    private RecordMedalDao recordMedalDao;
    private String userId;
    private int currentPoints;
    private ListView lvRankings;
    private SharedPreferences userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_exchange);

        userDao = new UserDao(this);
        medalDao = new MedalDao(this);
        recordMedalDao = new RecordMedalDao(this);
//        userId = getSharedPreferences("user_info", MODE_PRIVATE).getString("userId", "");
        userPrefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = userPrefs.getString("userId", "0");

        lvRankings = findViewById(R.id.lv_rankings);

        initViews();
        loadUserPoints();
        loadMedals();
        loadRankings();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        tvCurrentPoints = findViewById(R.id.tv_current_points);
        gvMedals = findViewById(R.id.gv_medals);
    }

    private void loadUserPoints() {
        currentPoints = userPrefs.getInt("points", 0);
        tvCurrentPoints.setText("当前积分: " + currentPoints);
    }

    private void loadMedals() {
        List<Medal> medalList = medalDao.getAllMedals();
        MedalAdapter adapter = new MedalAdapter(this, medalList);
        gvMedals.setAdapter(adapter);

        // 设置勋章点击事件
        gvMedals.setOnItemClickListener((parent, view, position, id) -> {
            Medal medal = medalList.get(position);
            showExchangeDialog(medal);
        });
    }

    private void showExchangeDialog(Medal medal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认兑换")
                .setMessage("确定要使用 " + medal.getmPoints() + " 积分兑换 " + medal.getmName() + " 勋章吗?")
                .setPositiveButton("确认", (dialog, which) -> {
                    if (currentPoints >= medal.getmPoints()) {
                        if (recordMedalDao.isOwn(userId, medal.getmId())) {
                            RecordMedal recordMedal = recordMedalDao.getUserMedalByMId(userId, medal.getmId());
                            if (recordMedalDao.updateRNum(userId, recordMedal.getrId(),
                                    recordMedal.getrNum() + 1) < 1) {
                                Toast.makeText(this, "兑换失败", Toast.LENGTH_SHORT).show();
                            }else {
                                int res = userDao.updateUserPoints(userId, currentPoints - medal.getmPoints());
                                if (res > 0) {
                                    updateShare(currentPoints - medal.getmPoints());
                                    Toast.makeText(this, "恭喜您成功兑换 " + medal.getmName()
                                            + " 勋章!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(this, "兑换失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            RecordMedal recordMedal = new RecordMedal();
                            recordMedal.setUserId(userId);
                            recordMedal.setmId(medal.getmId());
                            recordMedal.setrNum(1);
                            if (recordMedalDao.addMedal(recordMedal)) {
                                int res = userDao.updateUserPoints(userId, currentPoints - medal.getmPoints());
                                if (res > 0) {
                                    updateShare(currentPoints - medal.getmPoints());
                                    Toast.makeText(this, "恭喜您成功兑换 "
                                            + medal.getmName() + " 勋章!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(this, "兑换失败", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(this, "兑换失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "积分不足，无法兑换", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    private void loadRankings() {
        // 获取积分排名前5的用户
        List<User> topUsers = userDao.getTopUsers(5);
        RankingAdapter adapter = new RankingAdapter(this, topUsers);
        lvRankings.setAdapter(adapter);
    }

    private void updateShare(int points) {
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putInt("points", points);
        editor.apply();
    }
}