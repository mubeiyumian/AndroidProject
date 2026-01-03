package com.green;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.green.dao.ActivityDao;
import com.green.pojo.Activity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PublishActivity extends AppCompatActivity {

    private EditText etActivityName, etActivityContent, etActivityPlace, etActivityNum, etActivityDate;
    private Button btnPublish;
    private ActivityDao activityDao;
    private String userId;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        activityDao = new ActivityDao(this);
        userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
        calendar = Calendar.getInstance();

        initViews();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        etActivityName = findViewById(R.id.et_activity_name);
        etActivityContent = findViewById(R.id.et_activity_content);
        etActivityPlace = findViewById(R.id.et_activity_place);
        etActivityNum = findViewById(R.id.et_activity_num);
        etActivityDate = findViewById(R.id.et_activity_date);
        btnPublish = findViewById(R.id.btn_publish);

        // 设置日期选择器
        etActivityDate.setOnClickListener(v -> showDatePicker());

        // 设置默认日期为今天
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        etActivityDate.setText(dateFormat.format(new Date()));

        btnPublish.setOnClickListener(v -> publishActivity());
    }

    private void showDatePicker() {
        // 获取当前日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建日期选择器对话框，限制最小日期为今天
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    etActivityDate.setText(dateFormat.format(calendar.getTime()));
                },
                year, month, day
        );

        // 设置最小日期为今天
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void publishActivity() {
        String activityName = etActivityName.getText().toString().trim();
        String activityContent = etActivityContent.getText().toString().trim();
        String activityPlace = etActivityPlace.getText().toString().trim();
        String activityNumStr = etActivityNum.getText().toString().trim();
        String activityDate = etActivityDate.getText().toString().trim();

        // 验证输入
        if (activityName.isEmpty()) {
            Toast.makeText(this, "请输入活动名称", Toast.LENGTH_SHORT).show();
            return;
        }

        if (activityContent.isEmpty()) {
            Toast.makeText(this, "请输入活动内容", Toast.LENGTH_SHORT).show();
            return;
        }

        if (activityPlace.isEmpty()) {
            Toast.makeText(this, "请输入活动地点", Toast.LENGTH_SHORT).show();
            return;
        }

        if (activityNumStr.isEmpty()) {
            Toast.makeText(this, "请输入活动人数", Toast.LENGTH_SHORT).show();
            return;
        }

        int activityNum = Integer.parseInt(activityNumStr);
        if (activityNum <= 0) {
            Toast.makeText(this, "活动人数必须大于0", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建新活动
        Activity activity = new Activity();
        activity.setUserId(userId);
        activity.setaName(activityName);
        activity.setaContent(activityContent);
        activity.setaPlace(activityPlace);
        activity.setaNum(activityNum);
        activity.setaTime(activityDate);

        // 插入数据库
        long result = activityDao.addActivity(activity);
        if (result != -1) {
            Toast.makeText(this, "活动发布成功", Toast.LENGTH_SHORT).show();
            finish();  // 返回上一页
        } else {
            Toast.makeText(this, "活动发布失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}