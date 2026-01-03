package com.green;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPrefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        // 检查是否已经登录
        if (isUserLoggedIn()) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }, 1000);
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }, 1000);
        }
//        new Handler().postDelayed(() -> {
//            startActivity(new Intent(MainActivity.this, HomeActivity.class));
//            finish();
//        }, 1000);
    }

    private boolean isUserLoggedIn() {
        // 检查 SharedPreferences 中是否存在用户名
        return userPrefs.contains("username");
    }
}
