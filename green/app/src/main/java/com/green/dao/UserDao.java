package com.green.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.green.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private GreenDatabaseHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 添加用户
    public long addUser(String userId, String username, String password, String avatar,
                        String phone, int points) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GreenDatabaseHelper.USERID, userId);
        values.put(GreenDatabaseHelper.USERNAME, username);
        values.put(GreenDatabaseHelper.PASSWORD, password);
        values.put(GreenDatabaseHelper.AVATAR, avatar);
        values.put(GreenDatabaseHelper.PHONE, phone);
        values.put(GreenDatabaseHelper.POINTS, points);
        long id = db.insert(GreenDatabaseHelper.TABLE_USER, null, values);
        db.close();
        return id;
    }

    public User findUserByName(String etusername, String etpassword) {
//        String selectQuery = "SELECT * FROM " + GreenDatabaseHelper.TABLE_USER + "WHERE username = " + etusername;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                GreenDatabaseHelper.USERID,
                GreenDatabaseHelper.USERNAME,
                GreenDatabaseHelper.PASSWORD,
                GreenDatabaseHelper.AVATAR,
                GreenDatabaseHelper.PHONE,
                GreenDatabaseHelper.POINTS
        };
        String selection = GreenDatabaseHelper.USERNAME + " = ?";
        String[] selectionArgs = { etusername };

        Cursor cursor = db.query(
                GreenDatabaseHelper.TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setUserId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.PASSWORD)));
            user.setAvatar(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.AVATAR)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.PHONE)));
            user.setPoints(cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.POINTS)));
            cursor.close();
        }
        dbHelper.close();
        if (etpassword.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
    public User findUserById(String userId) {
//        String selectQuery = "SELECT * FROM " + GreenDatabaseHelper.TABLE_USER + "WHERE username = " + etusername;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                GreenDatabaseHelper.USERID,
                GreenDatabaseHelper.USERNAME,
                GreenDatabaseHelper.PASSWORD,
                GreenDatabaseHelper.AVATAR,
                GreenDatabaseHelper.PHONE,
                GreenDatabaseHelper.POINTS
        };
        String selection = GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = { userId };

        Cursor cursor = db.query(
                GreenDatabaseHelper.TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setUserId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.PASSWORD)));
            user.setAvatar(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.AVATAR)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.PHONE)));
            user.setPoints(cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.POINTS)));
            cursor.close();
        }
        dbHelper.close();
        return user;
    }

    // 获取所有用户
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + GreenDatabaseHelper.TABLE_USER;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(cursor.getString(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setAvatar(cursor.getString(3));
                user.setPhone(cursor.getString(4));
                user.setPoints(cursor.getInt(5));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    // 其他数据操作方法可以在此添加...
    // 更新用户名
    public int updateUsername(String userId, String newUsername) {
        Log.d("database", "更新");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GreenDatabaseHelper.USERNAME, newUsername);

        int rowsAffected = db.update(
                GreenDatabaseHelper.TABLE_USER,
                values,
                GreenDatabaseHelper.USERID + " = ?",
                new String[]{userId}
        );

        db.close();
        return rowsAffected;
    }

    // 更新手机号
    public int updatePhone(String userId, String newPhone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GreenDatabaseHelper.PHONE, newPhone);

        int rowsAffected = db.update(
                GreenDatabaseHelper.TABLE_USER,
                values,
                GreenDatabaseHelper.USERID + " = ?",
                new String[]{userId}
        );

        db.close();
        return rowsAffected;
    }

    // 更新密码
    public int updatePassword(String userId, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GreenDatabaseHelper.PASSWORD, newPassword);

        int rowsAffected = db.update(
                GreenDatabaseHelper.TABLE_USER,
                values,
                GreenDatabaseHelper.USERID + " = ?",
                new String[]{userId}
        );

        db.close();
        return rowsAffected;
    }

    // 获取用户积分
    public int getUserPoints(String userId) {
        int points = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.POINTS};
        String selection = GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_USER, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            points = cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.POINTS));
        }

        cursor.close();
        db.close();
        return points;
    }

    // 获取积分排名前n的用户
    public List<User> getTopUsers(int count) {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.USERID, GreenDatabaseHelper.USERNAME, GreenDatabaseHelper.POINTS};
        String orderBy = GreenDatabaseHelper.POINTS + " DESC LIMIT " + count;

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_USER, columns, null, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                String uId = cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERID));
                String username = cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.USERNAME));
                int points = cursor.getInt(cursor.getColumnIndex(GreenDatabaseHelper.POINTS));

                User user = new User();
                user.setUserId(uId);
                user.setUsername(username);
                user.setPoints(points);

                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    // 更新用户积分
    public int updateUserPoints(String userId, int points) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GreenDatabaseHelper.POINTS, points);

        String selection = GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = {userId};

        int rowsAffected = db.update(GreenDatabaseHelper.TABLE_USER, values, selection, selectionArgs);
        db.close();

        return rowsAffected;
    }

    // 检查用户ID是否已存在
    public boolean isUserIdExists(String userId) {
        boolean exists = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.USERID};
        String selection = GreenDatabaseHelper.USERID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_USER, columns, selection, selectionArgs, null, null, null);
        exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }

    // 检查用户名是否已存在
    public boolean isUsernameExists(String username) {
        boolean exists = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.USERNAME};
        String selection = GreenDatabaseHelper.USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_USER, columns, selection, selectionArgs, null, null, null);
        exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }

    // 检查手机号是否已存在
    public boolean isPhoneExists(String phone) {
        boolean exists = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {GreenDatabaseHelper.PHONE};
        String selection = GreenDatabaseHelper.PHONE + " = ?";
        String[] selectionArgs = {phone};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_USER, columns, selection, selectionArgs, null, null, null);
        exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }

    // 插入新用户
    public boolean insertUser(User user) {
        // 生成唯一的用户ID
        String userId = user.getUserId();
        if (userId == null || userId.isEmpty()) {
            do {
                userId = User.generateUserId();
            } while (isUserIdExists(userId));
            user.setUserId(userId);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(GreenDatabaseHelper.USERID, user.getUserId());
        values.put(GreenDatabaseHelper.USERNAME, user.getUsername());
        values.put(GreenDatabaseHelper.PASSWORD, user.getPassword());
        values.put(GreenDatabaseHelper.PHONE, user.getPhone());
        values.put(GreenDatabaseHelper.POINTS, user.getPoints());

        long newRowId = db.insert(GreenDatabaseHelper.TABLE_USER, null, values);
        db.close();

        return newRowId != -1;
    }
}

