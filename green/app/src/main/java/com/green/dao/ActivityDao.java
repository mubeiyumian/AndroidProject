package com.green.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.green.pojo.Activity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityDao {
    private GreenDatabaseHelper dbHelper;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    public ActivityDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 添加新活动
    public long addActivity(Activity activity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(GreenDatabaseHelper.AID, Activity.generateAId());
        values.put(GreenDatabaseHelper.USERID, activity.getUserId());
        values.put(GreenDatabaseHelper.ANAME, activity.getaName());
        values.put(GreenDatabaseHelper.ACONTENT, activity.getaContent());
        values.put(GreenDatabaseHelper.APLACE, activity.getaPlace());
        values.put(GreenDatabaseHelper.ANUM, activity.getaNum());
        values.put(GreenDatabaseHelper.ATIME, activity.getaTime());

        long newRowId = db.insert(GreenDatabaseHelper.ACTIVITY_TABLE, null, values);
        db.close();
        return newRowId;
    }

    // 获取所有活动（按时间降序排列）
    public List<Activity> getAllActivities() {
        List<Activity> activityList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                GreenDatabaseHelper.AID, GreenDatabaseHelper.USERID, GreenDatabaseHelper.ANAME,
                GreenDatabaseHelper.ACONTENT, GreenDatabaseHelper.APLACE, GreenDatabaseHelper.ANUM, GreenDatabaseHelper.ATIME
        };
        String orderBy = GreenDatabaseHelper.ATIME + " DESC";  // 按时间降序排列

        Cursor cursor = db.query(GreenDatabaseHelper.ACTIVITY_TABLE, columns, null, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                Activity activity = new Activity();
                activity.setaId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.AID)));
                activity.setUserId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERID)));
                activity.setaName(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ANAME)));
                activity.setaContent(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ACONTENT)));
                activity.setaPlace(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.APLACE)));
                activity.setaNum(cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.ANUM)));
                activity.setaTime(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ATIME)));

                activityList.add(activity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return activityList;
    }

    // 根据ID获取活动详情
    public Activity getActivityById(String activityId) {
        Activity activity = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                GreenDatabaseHelper.AID, GreenDatabaseHelper.USERID, GreenDatabaseHelper.ANAME,
                GreenDatabaseHelper.ACONTENT, GreenDatabaseHelper.APLACE, GreenDatabaseHelper.ANUM, GreenDatabaseHelper.ATIME
        };
        String selection = GreenDatabaseHelper.AID + " = ?";
        String[] selectionArgs = {activityId};

        Cursor cursor = db.query(GreenDatabaseHelper.ACTIVITY_TABLE, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            activity = new Activity();
            activity.setaId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.AID)));
            activity.setUserId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERID)));
            activity.setaName(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ANAME)));
            activity.setaContent(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ACONTENT)));
            activity.setaPlace(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.APLACE)));
            activity.setaNum(cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.ANUM)));
            activity.setaTime(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ATIME)));
        }

        cursor.close();
        db.close();
        return activity;
    }

    public List<Activity> getActivitiesByUserId(String userId) {
        List<Activity> activityList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                GreenDatabaseHelper.AID, GreenDatabaseHelper.USERID, GreenDatabaseHelper.ANAME,
                GreenDatabaseHelper.ACONTENT, GreenDatabaseHelper.APLACE, GreenDatabaseHelper.ANUM, GreenDatabaseHelper.ATIME
        };
        String selection = GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = {userId};
        String orderBy = GreenDatabaseHelper.ATIME + " DESC";  // 按时间降序排列

        Cursor cursor = db.query(GreenDatabaseHelper.ACTIVITY_TABLE, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                Activity activity = new Activity();
                activity.setaId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.AID)));
                activity.setUserId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERID)));
                activity.setaName(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ANAME)));
                activity.setaContent(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ACONTENT)));
                activity.setaPlace(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.APLACE)));
                activity.setaNum(cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.ANUM)));
                activity.setaTime(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ATIME)));

                activityList.add(activity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return activityList;
    }
}
