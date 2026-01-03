package com.green.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.green.pojo.Medal;
import java.util.ArrayList;
import java.util.List;

public class MedalDao {
    private GreenDatabaseHelper dbHelper;

    public MedalDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 获取所有勋章
    public List<Medal> getAllMedals() {
        List<Medal> medals = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.MID, GreenDatabaseHelper.MNAME, GreenDatabaseHelper.MIMG, GreenDatabaseHelper.MPOINTS};
        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_MEDAL, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String mId = cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.MID));
                String mName = cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.MNAME));
                String mImg = cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.MIMG));
                int mPoints = cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.MPOINTS));

                medals.add(new Medal(mId, mName, mImg, mPoints));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return medals;
    }

}
