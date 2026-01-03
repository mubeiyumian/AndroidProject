package com.green.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.green.pojo.Activity;
import com.green.pojo.ParticipationActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParticipationActivityDao {
    private GreenDatabaseHelper dbHelper;

    public ParticipationActivityDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 添加用户参与活动记录
    public long addParticipation(String activityId, String userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(GreenDatabaseHelper.PID, ParticipationActivity.generatePId());
        values.put(GreenDatabaseHelper.AID, activityId);
        values.put(GreenDatabaseHelper.USERID, userId);

        long newRowId = db.insert(GreenDatabaseHelper.PACTIVITY_TABLE, null, values);
        db.close();
        return newRowId;
    }

    // 获取用户参与的所有活动
    public List<ParticipationActivity> getUserParticipations(String userId) {
        List<ParticipationActivity> participationList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {GreenDatabaseHelper.PID, GreenDatabaseHelper.AID, GreenDatabaseHelper.USERID};
        String selection = GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(GreenDatabaseHelper.PACTIVITY_TABLE, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ParticipationActivity participation = new ParticipationActivity();
                participation.setpId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.PID)));
                participation.setaId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.AID)));
                participation.setUserId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERID)));

                participationList.add(participation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return participationList;
    }

    // 检查用户是否已参与活动
    public boolean isUserParticipated(String activityId, String userId) {
        boolean participated = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {GreenDatabaseHelper.PID};
        String selection = GreenDatabaseHelper.AID + " = ? AND " + GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = {activityId, userId};

        Cursor cursor = db.query(GreenDatabaseHelper.PACTIVITY_TABLE, columns, selection, selectionArgs, null, null, null);
        participated = cursor.moveToFirst();

        cursor.close();
        db.close();
        return participated;
    }

    public int getParticipantCount(String activityId) {
        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT COUNT(*) FROM " + GreenDatabaseHelper.PACTIVITY_TABLE + " WHERE " + GreenDatabaseHelper.AID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(activityId)});

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return count;
    }

    // 获取用户参加的离当前日期最近的活动
    public Activity getNearestParticipatedActivity(String userId) {
        List<Activity> activityList = new ArrayList<>();
        List<ParticipationActivity> participationList = getUserParticipations(userId);
        ActivityDao activityDao = new ActivityDao(dbHelper.getContext());

        for (ParticipationActivity participation : participationList) {
            Activity activity = activityDao.getActivityById(participation.getaId());
            if (activity != null) {
                activityList.add(activity);
            }
        }

        // 按活动时间排序
        Collections.sort(activityList, new Comparator<Activity>() {
            @Override
            public int compare(Activity a1, Activity a2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                try {
                    Date date1 = sdf.parse(a1.getaTime());
                    Date date2 = sdf.parse(a2.getaTime());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        // 获取当前日期
        Date currentDate = new Date();
        for (Activity activity : activityList) {
            try {
                Date activityDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(activity.getaTime());
                if (activityDate.after(currentDate) || activityDate.equals(currentDate)) {
                    return activity;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
