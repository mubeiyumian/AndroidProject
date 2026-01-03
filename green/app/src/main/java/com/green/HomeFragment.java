package com.green;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.fragment.app.Fragment;

import com.green.adapter.ActivityAdapter;
import com.green.dao.ActivityDao;
import com.green.pojo.Activity;
import java.util.List;

public class HomeFragment extends Fragment {

    private GridView gvActivities;
    private ActivityAdapter adapter;
    private ActivityDao activityDao;
    private List<Activity> activityList;

    private ActivityReminderReceiver reminderReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        activityDao = new ActivityDao(getActivity());
        initViews(view);
        loadActivities();

        // 注册广播接收器
        reminderReceiver = new ActivityReminderReceiver();
        IntentFilter filter = new IntentFilter("com.green.ACTIVITY_REMINDER");
        requireActivity().registerReceiver(reminderReceiver, filter);

        // 发送广播
        Intent intent = new Intent("com.green.ACTIVITY_REMINDER");
        requireActivity().sendBroadcast(intent);

        return view;
    }

    private void initViews(View view) {
        gvActivities = view.findViewById(R.id.gv_activities);

        // 设置点击事件
        gvActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = activityList.get(position);
                Intent intent = new Intent(getActivity(), ActivityDetail.class);
                intent.putExtra("activity", activity);
                startActivity(intent);
            }
        });
    }

    private void loadActivities() {
        // 获取所有活动
        activityList = activityDao.getAllActivities();

        // 设置适配器
        adapter = new ActivityAdapter(getActivity(), activityList);
        gvActivities.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadActivities();  // 刷新数据
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 注销广播接收器
        requireActivity().unregisterReceiver(reminderReceiver);
    }
}
