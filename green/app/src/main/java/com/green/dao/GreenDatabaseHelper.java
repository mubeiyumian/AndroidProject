package com.green.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class GreenDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "green_test";
    private static final int DATABASE_VERSION = 1;

    // 用户表
    public static final String TABLE_USER = "user";
    public static final String USERID = "userId";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AVATAR = "avatar";
    public static final String PHONE = "phone";
    public static final String POINTS = "points";

    // 勋章表
    public static final String TABLE_MEDAL = "medal";
    public static final String MID = "mId";
    public static final String MNAME = "mname";
    public static final String MIMG = "mImg";
    public static final String MPOINTS = "mPoints"; // 兑换所需积分

    //勋章获得表
    public static final String TABLE_RECORDMEDAL = "rmedal";
    public static final String RID = "rId";
//    public static final String MID = "mId";
//    public static final String USERID = "userId";
    public static final String RNUM = "rNum";

    // 环保记录表
    public static final String ECO_TABLE = "eco";
    public static final String ECOID = "ecoId";
    public static final String ECONAME = "econame";
    public static final String ECOCONTENT = "ecocontent";
    public static final String ECOTIME = "ecotime";
    public static final String ECOPOINTS = "ecopoints";  // 活动获得的积分

    // 活动表
    public static final String ACTIVITY_TABLE = "activity";
    public static final String AID = "aId";
//    public static final String USERID = "userId"; //发布该活动用户ID
    public static final String ANAME = "aname"; //活动名称
    public static final String ACONTENT = "acontent"; //活动内容
    public static final String APLACE = "aplace"; //活动地点
    public static final String ANUM = "anum"; //活动人数
    public static final String ATIME = "atime"; //活动开始时间

    //pactivity关联表
    public static final String PACTIVITY_TABLE = "pactivity";
    public static final String PID = "pId";
    //复用上面ID
//    public static final String AID = "aId";
//    public static final String USERId = "userId";

    // knowledge环保知识表
    public static final String TABLE_KNOWLEDGE = "knowledge";
    public static final String KID = "kId";
    public static final String KNAME = "kName";
    public static final String KCONTENT = "kContent";

    // 公益活动表信息
    public static final String TABLE_ONLINE = "online";
    public static final String ONLINEID = "activityId";
    public static final String ORGANIZATION = "organization";
    public static final String REMAININGSLOTS = "remainingSlots";
    public static final String ACTIVITYNAME = "activityName";
    public static final String ODATE = "date";

    // favorite
    public static final String TABLE_FAVORITE = "favorite";
    public static final String FID = "fId";
    public static final String F_AID = "f_aId";
    public static final String F_USERID = "f_userId";

    // 创建用户表
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER + " (" +
                    USERID + " VARCHAR(20) PRIMARY KEY, " +
                    USERNAME + " VARCHAR(20), " +
                    PASSWORD + " VARCHAR(20), " +
                    AVATAR + " VARCHAR(20), " +
                    PHONE + " VARCHAR(20), " +
                    POINTS + " INTEGER)";

    // 创建勋章表SQL
    private static final String CREATE_TABLE_MEDAL =
            "CREATE TABLE " + TABLE_MEDAL + " (" +
            MID + " VARCHAR(20) PRIMARY KEY, " +
            MNAME + " VARCHAR(20), " +
            MIMG + " VARCHAR(20), " +
            MPOINTS + " INTEGER)";

    // 创建勋章获得表SQL
    private static final String CREATE_TABLE_RMEDAL =
            "CREATE TABLE " + TABLE_RECORDMEDAL + " (" +
                    RID + " VARCHAR(20) PRIMARY KEY, " +
                    MID + " VARCHAR(20), " +
                    USERID + " VARCHAR(20), " +
                    RNUM + " INTEGER)";

    // 创建活动记录表SQL
    private static final String CREATE_TABLE_ECO =
            "CREATE TABLE " + ECO_TABLE + " (" +
            ECOID + " VARCHAR(20) PRIMARY KEY, " +
            USERID + " VARCHAR(20), " +
            ECONAME + " VARCHAR(20), " +
            ECOCONTENT + " VARCHAR(20), " +
            ECOTIME + " INTEGER, " +
            ECOPOINTS + " INTEGER)";

    // 创建活动表SQL
    private static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + ACTIVITY_TABLE + " (" +
            AID + " VARCHAR(20) PRIMARY KEY, " +
            USERID + " VARCHAR(20), " +
            ANAME + " VARCHAR(20), " +
            ACONTENT + " VARCHAR(20), " +
            APLACE + " VARCHAR(20), " +
            ANUM + " INTEGER, " +
            ATIME + " VARCHAR(20) )";

    // 创建活动关联表SQL
    private static final String CREATE_TABLE_PACTIVITY = "CREATE TABLE " + PACTIVITY_TABLE + " (" +
            PID + " VARCHAR(20) PRIMARY KEY, " +
            AID + " VARCHAR(20), " +
            USERID + " VARCHAR(20) )";

    // 创建环保知识表语句
    private static final String CREATE_TABLE_KNOWLEDGE = "CREATE TABLE " + TABLE_KNOWLEDGE + "("
            + KID + " VARCHAR(20) PRIMARY KEY,"
            + KNAME + " VARCHAR(20) ,"
            + KCONTENT + " VARCHAR(20) )";

    // 线上公益活动创建表语句
    private static final String CREATE_TABLE_ONLINE = "CREATE TABLE " + TABLE_ONLINE + "("
            + ONLINEID + " VARCHAR(20) PRIMARY KEY,"
            + ORGANIZATION + " VARCHAR(20) ,"
            + REMAININGSLOTS + " VARCHAR(20) ,"
            + ACTIVITYNAME + " VARCHAR(20) ,"
            + ODATE + " VARCHAR(20) )";

    // 关注表
    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE + " (" +
                    FID + " VARCHAR(20) PRIMARY KEY, " +
                    F_AID + " VARCHAR(20), " +
                    F_USERID + " VARCHAR(20) )";

    private Context context;

    public GreenDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TABLE_MEDAL);
        db.execSQL(CREATE_TABLE_RMEDAL);
        db.execSQL(CREATE_TABLE_ECO);
        db.execSQL(CREATE_TABLE_ACTIVITY);
        db.execSQL(CREATE_TABLE_PACTIVITY);
        db.execSQL(CREATE_TABLE_KNOWLEDGE);
        db.execSQL(CREATE_TABLE_ONLINE);
        db.execSQL(CREATE_TABLE_FAVORITE);
        insertInitialData(db);
        insertInitialMedals(db); // 插入初始勋章数据
        insertInitialKnowledgeData(db);
        insertInitialOnlineData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        String insert1 = "INSERT INTO " + TABLE_USER + " (" +
                USERID + ", " + USERNAME + ", " + PASSWORD + ", " + AVATAR + ", " +
                PHONE + ", " + POINTS + ") VALUES (" +
                "'1', 'xiaoming', '123456', '/avatar.jpg', '11111111111', 0)";

        String insert2 = "INSERT INTO " + TABLE_USER + " (" +
                USERID + ", " + USERNAME + ", " + PASSWORD + ", " + AVATAR + ", " +
                PHONE + ", " + POINTS + ") VALUES (" +
                "'2', 'xiaolin', '123456', '/avatar.jpg', '22222222222', 20)";

        String insert3 = "INSERT INTO " + TABLE_RECORDMEDAL + " (" +
                RID + ", " + MID + ", " + USERID + ", " + RNUM + ") VALUES (" +
                "'1', '1', '1', 1)";

        String insert4 = "INSERT INTO " + ACTIVITY_TABLE + " (" +
                AID + ", " + USERID + ", " + ANAME + ", " + ACONTENT + ", " + APLACE + ", " + ANUM + ", " + ATIME + ") VALUES (" +
                "'1', '1', '城市公园植树活动', '与环保组织一起参与城市公园植树，为城市增添绿色。', '城市中央公园', 3, '2025-06-01')";

        String insert5 = "INSERT INTO " + ACTIVITY_TABLE + " (" +
                AID + ", " + USERID + ", " + ANAME + ", " + ACONTENT + ", " + APLACE + ", " + ANUM + ", " + ATIME + ") VALUES (" +
                "'2', '1', '海滩清洁行动', '清理海滩垃圾，保护海洋生态环境。', '东海海滩', 10, '2025-06-05')";

        String insert6 = "INSERT INTO " + ACTIVITY_TABLE + " (" +
                AID + ", " + USERID + ", " + ANAME + ", " + ACONTENT + ", " + APLACE + ", " + ANUM + ", " + ATIME + ") VALUES (" +
                "'3', '1', '222', '2222222222', '2222', 5, '2025-06-03')";

        db.execSQL(insert1);
        db.execSQL(insert2);
        db.execSQL(insert3);
        db.execSQL(insert4);
        db.execSQL(insert5);
        db.execSQL(insert6);
    }

    // 插入初始勋章数据
    private void insertInitialMedals(SQLiteDatabase db) {
        String[] medalNames = {"环保先锋", "节能达人", "低碳卫士", "节水标兵", "绿色出行", "循环利用"};
        String[] medalImages = {"medal_1", "medal_2", "medal_3", "medal_4", "medal_5", "medal_6"};
        int[] medalPoints = {100, 200, 300, 400, 500, 600};
        String[] index = {"1", "2", "3", "4", "5", "6"};

        for (int i = 0; i < medalNames.length; i++) {
            String sql = "INSERT INTO " + TABLE_MEDAL + " (" +
                    MID + ", " + MNAME + ", " + MIMG + ", " + MPOINTS + ") VALUES ('" + index[i] + "', '" +
                    medalNames[i] + "', '" + medalImages[i] + "', " + medalPoints[i] + ")";
            db.execSQL(sql);
        }
    }

    private void insertInitialKnowledgeData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // 知识1
        values.put(KID, "1");
        values.put(KNAME, "垃圾分类知识");
        values.put(KCONTENT, "垃圾分类是对垃圾收集处置传统方式的改革，垃圾分类一般分为可回收垃圾，厨余垃圾，不可回收垃圾，有害垃圾等");
        db.insert(TABLE_KNOWLEDGE, null, values);
        values.clear();

        // 知识2
        values.put(KID, "2");
        values.put(KNAME, "节约用水技巧");
        values.put(KCONTENT, "1. 刷牙时关上水龙头；2. 使用节水工具");
        db.insert(TABLE_KNOWLEDGE, null, values);
        values.clear();

        // 知识3
        values.put(KID, "3");
        values.put(KNAME, "可再生能源介绍");
        values.put(KCONTENT, "可再生能源包括太阳能、风能、水能");
        db.insert(TABLE_KNOWLEDGE, null, values);
        values.clear();

        // 知识4
        values.put(KID, "4");
        values.put(KNAME, "环保袋的重要性");
        values.put(KCONTENT, "使用环保袋可以减少塑料袋的使用");
        db.insert(TABLE_KNOWLEDGE, null, values);
        values.clear();

        // 知识5
        values.put(KID, "5");
        values.put(KNAME, "电子垃圾处理");
        values.put(KCONTENT, "电子垃圾含有有害物质，应交给专业回收机构处理，可在线查询所在地区的专业回收机构");
        db.insert(TABLE_KNOWLEDGE, null, values);
    }

    private void insertInitialOnlineData(SQLiteDatabase db) {
        try {
            // 从assets目录读取output.json文件
            InputStream is = getContext().getAssets().open("output.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonStr = new String(buffer, "UTF-8");

            // 解析JSON数据
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();

                values.put(ONLINEID, obj.getString("activityId"));
                values.put(ORGANIZATION, obj.getString("organization"));
                values.put(REMAININGSLOTS, obj.getString("remainingSlots"));
                values.put(ACTIVITYNAME, obj.getString("activityName"));
                values.put(ODATE, obj.getString("date"));

                db.insert(TABLE_ONLINE, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

