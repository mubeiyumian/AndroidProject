package com.green.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDao {
    private GreenDatabaseHelper dbHelper;

    public FavoriteDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 添加收藏
    public long addFavorite(String fId, String activityId, String userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GreenDatabaseHelper.FID, fId);
        values.put(GreenDatabaseHelper.F_AID, activityId);
        values.put(GreenDatabaseHelper.F_USERID, userId);

        long newRowId = db.insert(GreenDatabaseHelper.TABLE_FAVORITE, null, values);
        db.close();
        return newRowId;
    }

    // 取消收藏
    public int removeFavorite(String activityId, String userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = GreenDatabaseHelper.F_AID + " = ? AND " + GreenDatabaseHelper.F_USERID + " = ?";
        String[] selectionArgs = {activityId, userId};

        int rowsAffected = db.delete(GreenDatabaseHelper.TABLE_FAVORITE, selection, selectionArgs);
        db.close();
        return rowsAffected;
    }

    // 检查是否已收藏
    public boolean isFavorite(String activityId, String userId) {
        boolean isFav = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.FID};
        String selection = GreenDatabaseHelper.F_AID + " = ? AND " + GreenDatabaseHelper.F_USERID + " = ?";
        String[] selectionArgs = {activityId, userId};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_FAVORITE, columns, selection, selectionArgs, null, null, null);
        isFav = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isFav;
    }

    // 获取用户收藏的活动ID列表
    public List<String> getFavoriteActivityIds(String userId) {
        List<String> activityIds = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.F_AID};
        String selection = GreenDatabaseHelper.F_USERID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_FAVORITE, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String activityId = cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.F_AID));
                activityIds.add(activityId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return activityIds;
    }
}
