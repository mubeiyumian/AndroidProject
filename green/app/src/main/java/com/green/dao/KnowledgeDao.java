package com.green.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.green.pojo.Knowledge;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeDao {
    private GreenDatabaseHelper dbHelper;

    public KnowledgeDao(Context context) {
        dbHelper = new GreenDatabaseHelper(context);
    }

    // 获取所有环保知识
    public List<Knowledge> getAllKnowledge() {
        List<Knowledge> knowledgeList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {GreenDatabaseHelper.KID, GreenDatabaseHelper.KNAME, GreenDatabaseHelper.KCONTENT};
        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_KNOWLEDGE, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Knowledge knowledge = new Knowledge();
                knowledge.setkId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.KID)));
                knowledge.setkName(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.KNAME)));
                knowledge.setkContent(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.KCONTENT)));
                knowledgeList.add(knowledge);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return knowledgeList;
    }

    // 根据ID获取知识
    public Knowledge getKnowledgeById(String id) {
        Knowledge knowledge = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {GreenDatabaseHelper.KID, GreenDatabaseHelper.KNAME, GreenDatabaseHelper.KCONTENT};
        String selection = GreenDatabaseHelper.KID + " = ?";
        String[] selectionArgs = {id};

        Cursor cursor = db.query(GreenDatabaseHelper.TABLE_KNOWLEDGE, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            knowledge = new Knowledge();
            knowledge.setkId(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.KID)));
            knowledge.setkName(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.KNAME)));
            knowledge.setkContent(cursor.getString(cursor.getColumnIndex(GreenDatabaseHelper.KCONTENT)));
        }

        cursor.close();
        db.close();
        return knowledge;
    }
}
