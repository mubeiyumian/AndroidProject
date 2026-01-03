package com.green.pojo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Activity implements Serializable {
    private String aId;
    private String userId;
    private String aName;
    private String aContent;
    private String aPlace;
    private int aNum;
    private String aTime;  // 格式：yyyy-MM-dd

    public static String generateAId() {
        return UUID.randomUUID().toString();
    }

    // 日期格式化工具
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    // 获取格式化后的日期显示
    public String getFormattedDate() {
        try {
            Date date = DATE_FORMAT.parse(aTime);
            SimpleDateFormat displayFormat = new SimpleDateFormat("MM月dd日", Locale.CHINA);
            return displayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return aTime;
        }
    }

    // Getters and setters
    public String getaId() { return aId; }
    public void setaId(String aId) { this.aId = aId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getaName() { return aName; }
    public void setaName(String aName) { this.aName = aName; }
    public String getaContent() { return aContent; }
    public void setaContent(String aContent) { this.aContent = aContent; }
    public String getaPlace() { return aPlace; }
    public void setaPlace(String aPlace) { this.aPlace = aPlace; }
    public int getaNum() { return aNum; }
    public void setaNum(int aNum) { this.aNum = aNum; }
    public String getaTime() { return aTime; }
    public void setaTime(String aTime) { this.aTime = aTime; }
}
