package com.green;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.green.adapter.KnowledgeAdapter;
import com.green.pojo.Knowledge;
import com.green.dao.KnowledgeDao;

import java.util.ArrayList;
import java.util.List;

public class EcoKnowledgeActivity extends AppCompatActivity {
    private ListView lvKnowledge;
    private KnowledgeAdapter adapter;
    private List<Knowledge> knowledgeList = new ArrayList<>();
    private KnowledgeDao knowledgeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_knowledge);

        knowledgeDao = new KnowledgeDao(this);

        initViews();
        loadKnowledgeData();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        lvKnowledge = findViewById(R.id.lv_knowledge);
        adapter = new KnowledgeAdapter(this, knowledgeList);
        lvKnowledge.setAdapter(adapter);

        // 设置列表项点击事件
        lvKnowledge.setOnItemClickListener((parent, view, position, id) -> {
            Knowledge knowledge = knowledgeList.get(position);
            openKnowledgeDetail(knowledge);
        });
    }

    private void loadKnowledgeData() {
        new Thread(() -> {
            List<Knowledge> tempList = knowledgeDao.getAllKnowledge();

            runOnUiThread(() -> {
                knowledgeList.clear();
                knowledgeList.addAll(tempList);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void openKnowledgeDetail(Knowledge knowledge) {
        Intent intent = new Intent(this, KnowledgeDetailActivity.class);
        intent.putExtra("knowledge", knowledge);
        startActivity(intent);
    }
}