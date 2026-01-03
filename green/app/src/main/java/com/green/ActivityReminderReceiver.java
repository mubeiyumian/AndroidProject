package com.green;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.green.dao.ParticipationActivityDao;
import com.green.pojo.Activity;

public class ActivityReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.green.ACTIVITY_REMINDER".equals(intent.getAction())) {
            // 获取当前用户ID
            String userId = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("userId", "");
            ParticipationActivityDao participationDao = new ParticipationActivityDao(context);

            // 获取用户参加的离当前日期最近的活动
            Activity nearestActivity = participationDao.getNearestParticipatedActivity(userId);

            if (nearestActivity != null) {
                String message = "提醒：您参加的活动 " + nearestActivity.getaName() + " 将于 " + nearestActivity.getaTime() + " 开始";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}