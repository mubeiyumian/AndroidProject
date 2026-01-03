package com.green.pojo;

import java.util.UUID;

public class RecordMedal {
    private String rId;
    private String mId;
    private String userId;
    private int rNum;

    public static String generateRId() {
        return UUID.randomUUID().toString();
    }

    public RecordMedal() {

    }

    public RecordMedal(String rId, String mId, String userId, int rNum) {
        this.rId = rId;
        this.mId = mId;
        this.userId = userId;
        this.rNum = rNum;
    }

    // Getters and setters
    public String getrId() { return rId; }
    public void setrId(String rId) { this.rId = rId; }
    public String getmId() { return mId; }
    public void setmId(String mId) { this.mId = mId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public int getrNum() { return rNum; }
    public void setrNum(int rNum) { this.rNum = rNum; }
}
