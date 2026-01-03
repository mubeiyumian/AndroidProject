package com.green;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.green.pojo.Knowledge;

public class KnowledgeDetailActivity extends AppCompatActivity {
    private TextView tvKnowledgeTitle, tvKnowledgeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_detail);

        initViews();
        setupKnowledgeData();
    }

    private void initViews() {
        // 返回按钮
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        tvKnowledgeTitle = findViewById(R.id.tv_knowledge_title);
        tvKnowledgeContent = findViewById(R.id.tv_knowledge_content);
    }

    private void setupKnowledgeData() {
        Knowledge knowledge = (Knowledge) getIntent().getSerializableExtra("knowledge");
        if (knowledge != null) {
            tvKnowledgeTitle.setText(knowledge.getkName());
            tvKnowledgeContent.setText(knowledge.getkContent());
        }
    }
}