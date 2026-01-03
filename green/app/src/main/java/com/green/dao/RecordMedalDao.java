package com.green.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.green.pojo.RecordMedal;

import java.util.HashMap;
import java.util.Map;

public class RecordMedalDao {
    private GreenDatabaseHelper dbHelper;

    public RecordMedalDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 获取用户获得的所有勋章
    public Map<String, Integer> getUserMedals(String userId) {
        Map<String, Integer> medalMap = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.MID, GreenDatabaseHelper.RNUM};
        String selection = GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_RECORDMEDAL, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String mId = cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.MID));
                int rNum = cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.RNUM));
                Log.d("mId", mId);
                Log.d("rNum", "--" + rNum);
                medalMap.put(mId, rNum);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return medalMap;
    }

    public boolean addMedal(RecordMedal recordMedal) {

        String rId = RecordMedal.generateRId();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(GreenDatabaseHelper.RID, rId);
        values.put(GreenDatabaseHelper.MID, recordMedal.getmId());
        values.put(GreenDatabaseHelper.USERID, recordMedal.getUserId());
        values.put(GreenDatabaseHelper.RNUM, 1);

        long newRowId = db.insert(GreenDatabaseHelper.TABLE_RECORDMEDAL, null, values);
        db.close();

        return newRowId != -1;
    }

    public int updateRNum(String userId, String rId, int rNum) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GreenDatabaseHelper.RNUM, rNum);

        String selection = GreenDatabaseHelper.RID + " = ?";
        String[] selectionArgs = {rId};

        int rowsAffected = db.update(GreenDatabaseHelper.TABLE_RECORDMEDAL, values, selection, selectionArgs);
        db.close();

        return rowsAffected;
    }

    public RecordMedal getUserMedalByMId(String userId, String mId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                GreenDatabaseHelper.RID,
                GreenDatabaseHelper.USERID,
                GreenDatabaseHelper.MID,
                GreenDatabaseHelper.RNUM,
        };
        String selection = GreenDatabaseHelper.USERID + " = ?" + " and " + GreenDatabaseHelper.MID + " = ?";
        String[] selectionArgs = { userId, mId };

        Cursor cursor = db.query(
                GreenDatabaseHelper.TABLE_RECORDMEDAL,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        RecordMedal recordMedal = null;
        if (cursor != null && cursor.moveToFirst()) {
            recordMedal = new RecordMedal();
            recordMedal.setrId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.RID)));
            recordMedal.setmId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.MID)));
            recordMedal.setUserId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERID)));
            recordMedal.setrNum(cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.RNUM)));
            cursor.close();
        }
        dbHelper.close();
        return recordMedal;
    }

    public boolean isOwn(String userId, String mId) {
        boolean exists = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.USERID, GreenDatabaseHelper.MID};
        String selection = GreenDatabaseHelper.USERID + " = ?" + "and " + GreenDatabaseHelper.MID + " = ?";
        String[] selectionArgs = {userId, mId};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_RECORDMEDAL, columns, selection, selectionArgs, null, null, null);
        exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }
}
