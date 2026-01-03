package com.green;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.green.dao.EcoDao;
import com.green.pojo.Eco;
import com.green.dao.UserDao;

public class AddEcoActivity extends AppCompatActivity {

    private EditText etActivityName, etActivityContent;
    private RadioGroup rgTimeRange;
    private Button btnSubmit;
    private EcoDao ecoDao;
    private UserDao userDao;
    private String userId;
    private int newPoints;
    private SharedPreferences userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_eco);

        ecoDao = new EcoDao(this);
        userDao = new UserDao(this);
        userPrefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = userPrefs.getString("userId", "0");
        newPoints = userPrefs.getInt("points", 0);

        initViews();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        etActivityName = findViewById(R.id.et_activity_name);
        etActivityContent = findViewById(R.id.et_activity_content);
        rgTimeRange = findViewById(R.id.rg_time_range);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(v -> submitActivity());
    }

    private void submitActivity() {
        String activityName = etActivityName.getText().toString().trim();
        String activityContent = etActivityContent.getText().toString().trim();

        if (activityName.isEmpty()) {
            Toast.makeText(this, "请输入活动名称", Toast.LENGTH_SHORT).show();
            return;
        }

        if (activityContent.isEmpty()) {
            Toast.makeText(this, "请输入活动内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取选择的时间范围并转换为分钟
        int timeInMinutes = getSelectedTime();
        if (timeInMinutes == 0) {
            Toast.makeText(this, "请选择活动持续时间", Toast.LENGTH_SHORT).show();
            return;
        }

        // 计算积分
        int points = EcoDao.calculatePoints(timeInMinutes);
        newPoints += points;

        // 创建活动记录
        Eco activity = new Eco();
        activity.setUserId(userId);
        activity.setEcoName(activityName);
        activity.setEcoContent(activityContent);
        activity.setEcoTime(timeInMinutes);
        activity.setEcoPoints(points);

        // 保存到数据库
        long result = ecoDao.addActivity(activity);
        if (result != -1) {
            int newPoint = userDao.getUserPoints(userId);
            updateUserPoints(points + newPoint);  // 更新用户积分
            Toast.makeText(this, "活动记录添加成功，获得 " + points + " 积分", Toast.LENGTH_SHORT).show();
            finish();  // 返回上一页
        } else {
            Toast.makeText(this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private int getSelectedTime() {
        int selectedId = rgTimeRange.getCheckedRadioButtonId();

        switch (selectedId) {
            case R.id.rb_less_30:
                return 15;
            case R.id.rb_30_60:
                return 45;
            case R.id.rb_more_60:
                return 90;
            default:
                return 0;
        }
    }

    private void updateUserPoints(int points) {
        int res = userDao.updateUserPoints(userId, points);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putInt("points", points);
        editor.apply();
    }
}