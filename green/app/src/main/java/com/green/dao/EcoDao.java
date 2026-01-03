package com.green.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.green.pojo.Eco;

import java.util.ArrayList;
import java.util.List;

public class EcoDao {
    private GreenDatabaseHelper dbHelper;

    public EcoDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 检查用户ID是否已存在
    public boolean isEcoIdExists(String ecoId) {
        boolean exists = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.ECOID};
        String selection = GreenDatabaseHelper.ECOID + " = ?";
        String[] selectionArgs = {ecoId};

        Cursor cursor = db.query(GreenDatabaseHelper.ECO_TABLE, columns, selection, selectionArgs, null, null, null);
        exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }

    // 添加新活动记录
    public long addActivity(Eco activity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String ecoId = "";
        ecoId = Eco.generateEcoId();
        values.put(GreenDatabaseHelper.ECOID, ecoId);
        values.put(GreenDatabaseHelper.USERID, activity.getUserId());
        values.put(GreenDatabaseHelper.ECONAME, activity.getEcoName());
        values.put(GreenDatabaseHelper.ECOCONTENT, activity.getEcoContent());
        values.put(GreenDatabaseHelper.ECOTIME, activity.getEcoTime());
        values.put(GreenDatabaseHelper.ECOPOINTS, activity.getEcoPoints());

        long newRowId = db.insert(GreenDatabaseHelper.ECO_TABLE, null, values);
        db.close();
        return newRowId;
    }

    // 获取用户所有活动记录
    public List<Eco> getUserActivities(String userId) {
        List<Eco> activities = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                GreenDatabaseHelper.ECOID, GreenDatabaseHelper.ECONAME, GreenDatabaseHelper.ECOCONTENT,
                GreenDatabaseHelper.ECOTIME, GreenDatabaseHelper.ECOPOINTS
        };
        String selection = GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(
                GreenDatabaseHelper.ECO_TABLE, columns, selection, selectionArgs, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                Eco activity = new Eco();
                activity.setEcoId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ECOID)));
                activity.setEcoName(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ECONAME)));
                activity.setEcoContent(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.ECOCONTENT)));
                activity.setEcoTime(cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.ECOTIME)));
                activity.setEcoPoints(cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.ECOPOINTS)));

                activities.add(activity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return activities;
    }

    // 根据活动时间计算积分
    public static int calculatePoints(int timeInMinutes) {
        if (timeInMinutes <= 30) {
            return 20;
        } else if (timeInMinutes <= 60) {
            return 50;
        } else {
            return 70;
        }
    }
}
