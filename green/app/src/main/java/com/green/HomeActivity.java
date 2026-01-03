package com.green;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // 默认显示首页
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        Log.d(TAG, "当前页面：首页");
                        break;
                    case R.id.nav_carbon:
                        selectedFragment = new CarbonFragment();
                        Log.d(TAG, "当前页面：碳足迹");
                        break;
                    case R.id.nav_public:
                        selectedFragment = new PublicFragment();
                        Log.d(TAG, "当前页面：公益");
                        break;
                    case R.id.nav_my:
                        selectedFragment = new MyFragment();
                        Log.d(TAG, "当前页面：我的");
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            };
}
