package com.green;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.green.dao.UserDao;
import com.green.pojo.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etPhone;
    private Button btnRegister;
    private TextView tvLogin;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDao = new UserDao(this);
        initViews();
    }

    private void initViews() {
        // 返回按钮
//        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);

        btnRegister.setOnClickListener(v -> handleRegister());
        tvLogin.setOnClickListener(v -> finish());  // 返回登录页面
    }

    private void handleRegister() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查用户名是否已存在
        if (userDao.isUsernameExists(username)) {
            Toast.makeText(this, "该用户名已被注册", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查手机号是否已存在
        if (userDao.isPhoneExists(phone)) {
            Toast.makeText(this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setPoints(0);  // 新用户初始积分为0

        // 插入数据库
        boolean success = userDao.insertUser(user);
        if (success) {
            Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
            finish();  // 返回登录页面
        } else {
            Toast.makeText(this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}