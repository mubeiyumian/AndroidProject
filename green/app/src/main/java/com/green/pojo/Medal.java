package com.green.pojo;

public class Medal {
    private String mId;
    private String mName;
    private String mImg;
    private int mPoints;

    public Medal(String mId, String mName, String mImg, int mPoints) {
        this.mId = mId;
        this.mName = mName;
        this.mImg = mImg;
        this.mPoints = mPoints;
    }

    // Getters and setters
    public String getmId() { return mId; }
    public void setmId(String mId) { this.mId = mId; }
    public String getmName() { return mName; }
    public void setmName(String mName) { this.mName = mName; }
    public String getmImg() { return mImg; }
    public void setmImg(String mImg) { this.mImg = mImg; }
    public int getmPoints() { return mPoints; }
    public void setmPoints(int mPoints) { this.mPoints = mPoints; }
}
