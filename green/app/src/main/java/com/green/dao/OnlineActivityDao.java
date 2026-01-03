package com.green.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.green.pojo.OnlineActivity;

import java.util.ArrayList;
import java.util.List;

public class OnlineActivityDao {
    private GreenDatabaseHelper dbHelper;

    public OnlineActivityDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 获取所有公益活动
    public List<OnlineActivity> getAllActivities() {
        List<OnlineActivity> activityList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                GreenDatabaseHelper.ONLINEID,
                GreenDatabaseHelper.ORGANIZATION,
                GreenDatabaseHelper.REMAININGSLOTS,
                GreenDatabaseHelper.ACTIVITYNAME,
                GreenDatabaseHelper.ODATE
        };

        String orderBy = GreenDatabaseHelper.ODATE + " DESC";  // 按时间降序排列

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_ONLINE, columns, null, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                OnlineActivity activity = new OnlineActivity();
                activity.setActivityId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ONLINEID)));
                activity.setOrganization(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ORGANIZATION)));
                activity.setRemainingSlots(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.REMAININGSLOTS)));
                activity.setActivityName(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ACTIVITYNAME)));
                activity.setDate(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ODATE)));
                activityList.add(activity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return activityList;
    }

    // 根据ID获取活动
    public OnlineActivity getActivityById(String id) {
        OnlineActivity activity = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                GreenDatabaseHelper.ONLINEID,
                GreenDatabaseHelper.ORGANIZATION,
                GreenDatabaseHelper.REMAININGSLOTS,
                GreenDatabaseHelper.ACTIVITYNAME,
                GreenDatabaseHelper.ODATE
        };

        String selection = GreenDatabaseHelper.ONLINEID + " = ?";
        String[] selectionArgs = {id};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_ONLINE, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            activity = new OnlineActivity();
            activity.setActivityId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ONLINEID)));
            activity.setOrganization(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ORGANIZATION)));
            activity.setRemainingSlots(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.REMAININGSLOTS)));
            activity.setActivityName(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ACTIVITYNAME)));
            activity.setDate(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ODATE)));
        }

        cursor.close();
        db.close();
        return activity;
    }
}
