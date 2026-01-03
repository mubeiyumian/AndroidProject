package com.green;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.green.dao.UserDao;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private TextView tvUsername, tvPhone, tvPassword;
    private Button btnSave;

    private SharedPreferences prefs;
    private String username, phone, password, userId;
    private String avatarResId;
    private UserDao userDao;
    private boolean isInfoChanged = false; // 标记信息是否有变更

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        userDao = new UserDao(this); // 初始化UserDao

        ivAvatar = findViewById(R.id.iv_avatar);
        tvUsername = findViewById(R.id.tv_username);
        tvPhone = findViewById(R.id.tv_phone);
        tvPassword = findViewById(R.id.tv_password);
        btnSave = findViewById(R.id.btn_save);

        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        // 获取用户信息
        loadUserInfo();

        // 设置用户信息
        updateUI();

        // 设置点击事件
        setupClickListeners();
    }

    private void loadUserInfo() {
        username = prefs.getString("username", "");
        phone = prefs.getString("phone", "");
        password = prefs.getString("password", "");
        avatarResId = prefs.getString("avatar", " ");
        userId = prefs.getString("userId", "0");
    }

    private void updateUI() {
        tvUsername.setText(username);
        tvPhone.setText(phone);
        tvPassword.setText("******"); // 密码显示为星号

        Glide.with(this)
                .load(R.drawable.avatar)
                .circleCrop()
                .into(ivAvatar);
    }

    private void setupClickListeners() {
        // 用户名点击事件
        tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("username", username);
            }
        });

        // 手机号点击事件
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("phone", phone);
            }
        });

        // 密码点击事件
        tvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("password", "");
            }
        });

        // 保存按钮点击事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInfoChanged) {
                    saveUserInfoToDatabase(); // 保存到数据库
                } else {
                    Toast.makeText(ProfileActivity.this, "没有信息需要更新", Toast.LENGTH_SHORT).show();
                }
//                finish();
            }
        });
    }

    private void showEditDialog(final String field, String currentValue) {
        final EditText input = new EditText(this);
        input.setText(currentValue);
        input.setSelection(currentValue.length());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改" + getFieldName(field));
        builder.setView(input);

        builder.setPositiveButton("确定", (dialog, which) -> {
            String newValue = input.getText().toString().trim();
            if (validateInput(field, newValue)) {
                if (!newValue.equals(currentValue)) { // 检查是否有变更
                    updateField(field, newValue);
                    isInfoChanged = true;
                }
            }
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private String getFieldName(String field) {
        switch (field) {
            case "username":
                return "用户名";
            case "phone":
                return "手机号";
            case "password":
                return "密码";
            default:
                return "";
        }
    }

    private boolean validateInput(String field, String value) {
        if (value.isEmpty()) {
            Toast.makeText(this, getFieldName(field) + "不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (field.equals("username")) {
            if (userDao.isUsernameExists(value)) {
                Toast.makeText(this, "该用户名已被其他用户使用，请选择其他用户名", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (field.equals("phone")) {
            if (userDao.isPhoneExists(value)) {
                Toast.makeText(this, "该手机号已被其他用户使用，请选择其他手机号", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (field.equals("password")) {
            if (value.length() < 6) {
                Toast.makeText(this, "密码长度小于6位", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

//        if (field.equals("phone") && !value.matches("^1[3-9]\\d{9}$")) {
//            Toast.makeText(this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }

    private void updateField(String field, String value) {

        switch (field) {
            case "username":
                username = value;
                tvUsername.setText(username);
                break;
            case "phone":
                phone = value;
                tvPhone.setText(phone);
                break;
            case "password":
                password = value;
                tvPassword.setText("******");
                break;
        }

    }

    private void saveUserInfoToDatabase() {
        // 显示保存中提示
        Toast.makeText(this, "正在保存...", Toast.LENGTH_SHORT).show();

        // 开启线程执行数据库操作（避免阻塞UI）
        new Thread(() -> {
            boolean success = true;

            // 更新用户名
            if (userDao.updateUsername(userId, username) <= 0) {
                success = false;
            }

            // 更新手机号
            if (userDao.updatePhone(userId, phone) <= 0) {
                success = false;
            }

            if (userDao.updatePassword(userId, password) <= 0) {
                success = false;
            }

            final boolean finalSuccess = success;
            runOnUiThread(() -> {
                if (finalSuccess) {
                    updateShared();
                    Toast.makeText(ProfileActivity.this, "个人信息已成功更新", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "更新失败，请稍后重试", Toast.LENGTH_SHORT).show();
                }
//                finish();
            });
        }).start();
    }

    private void updateShared() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.putString("phone", phone);
        editor.apply();
    }
}