package com.green;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class PublicFragment extends Fragment {
    private View rootView;
    private Button btnEcoKnowledge, btnPublicActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_public, container, false);

        initViews();
        setupListeners();

        return rootView;
    }

    private void initViews() {
        btnEcoKnowledge = rootView.findViewById(R.id.btn_eco_knowledge);
        btnPublicActivity = rootView.findViewById(R.id.btn_public_activity);
    }

    private void setupListeners() {
        // 环保小知识按钮点击事件
        btnEcoKnowledge.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EcoKnowledgeActivity.class);
            startActivity(intent);
        });

        // 公益活动按钮点击事件
        btnPublicActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PublicListActivity.class);
            startActivity(intent);
//            Toast.makeText(getActivity(), "跳转到公益活动页面", Toast.LENGTH_SHORT).show();
        });
    }
}