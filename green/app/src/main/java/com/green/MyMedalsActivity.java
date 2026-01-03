// MyMedalsActivity.java
package com.green;

import android.os.Bundle;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import com.green.adapter.MyMedalsAdapter;
import com.green.dao.MedalDao;
import com.green.dao.RecordMedalDao;
import com.green.pojo.Medal;

import java.util.List;
import java.util.Map;

public class MyMedalsActivity extends AppCompatActivity {

    private GridView gvMyMedals;
    private MedalDao medalDao;
    private RecordMedalDao recordMedalDao;
    private String userId;
    private Map<String, Integer> userMedalsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medals);

        medalDao = new MedalDao(this);
        recordMedalDao = new RecordMedalDao(this);
        userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");

        initViews();
        loadMyMedals();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        gvMyMedals = findViewById(R.id.gv_my_medals);
    }

    private void loadMyMedals() {
        // 获取所有勋章
        List<Medal> allMedals = medalDao.getAllMedals();

        // 获取用户已获得的勋章
        userMedalsMap = recordMedalDao.getUserMedals(userId);

        // 设置适配器
        MyMedalsAdapter adapter = new MyMedalsAdapter(this, allMedals, userMedalsMap);
        gvMyMedals.setAdapter(adapter);
    }
}