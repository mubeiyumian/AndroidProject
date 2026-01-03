package com.green;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {

    private ImageView ivAvatar;
    private TextView tvUsername, tvPoints;
    private ListView lvMenu;
    private SharedPreferences userPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        userPrefs = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        initViews(view);
        setupUserInfo();
        setupMenuList();

        return view;
    }

    private void initViews(View view) {
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvUsername = view.findViewById(R.id.tv_username);
        tvPoints = view.findViewById(R.id.tv_points);
        lvMenu = view.findViewById(R.id.lv_menu);
    }

    private void setupUserInfo() {
        String username = userPrefs.getString("username", "未登录");
        int points = userPrefs.getInt("points", 0);

        tvUsername.setText(username);
        tvPoints.setText("积分: " + points);

        // 加载头像
        Glide.with(this)
                .load(R.drawable.avatar)
                .circleCrop()
                .into(ivAvatar);
    }

    private void setupMenuList() {
        List<String> menuItems = new ArrayList<>();
        menuItems.add("个人信息");
        menuItems.add("环保记录");
        menuItems.add("活动记录");
        menuItems.add("活动发布");
        menuItems.add("发布记录");
        menuItems.add("积分兑换");
        menuItems.add("勋章大全");
        menuItems.add("活动收藏");
        menuItems.add("退出登录");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        );

        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);

                switch (item) {
                    case "个人信息":
                        startActivity(new Intent(getActivity(), ProfileActivity.class));
                        break;
                    case "积分兑换":
                        startActivity(new Intent(getActivity(), PointsExchangeActivity.class));
                        break;
                    case "勋章大全":
                        startActivity(new Intent(getActivity(), MyMedalsActivity.class));
                        break;
                    case "环保记录":
                        startActivity(new Intent(getActivity(), EcoListActivity.class));
                        break;
                    case "活动发布":
                        startActivity(new Intent(getActivity(), PublishActivity.class));
                        break;
                    case "活动记录":
                        startActivity(new Intent(getActivity(), RecordsActivity.class));
                        break;
                    case "发布记录":
                        startActivity(new Intent(getActivity(), MyPublishActivity.class));
                        break;
                    case "活动收藏":
                        startActivity(new Intent(getActivity(), FavoriteActivity.class));
                        break;
                    case "退出登录":
                        logout();
                        break;
                    default:
                        showToast(item);
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message + "功能即将上线", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        // 清除用户信息
        userPrefs.edit().clear().apply();

        // 返回登录页面
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupUserInfo();  // 刷新数据
    }
}
