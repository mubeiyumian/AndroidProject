package com.green.pojo;

import java.util.UUID;

public class Eco {
    private String ecoId;
    private String userId;
    private String ecoName;
    private String ecoContent;
    private int ecoTime;    // 活动持续时间（分钟）
    private int ecoPoints;  // 获得的积分

    public static String generateEcoId() {
        return UUID.randomUUID().toString();
    }

    public Eco() {
    }

    public Eco(String ecoId, String userId, String ecoName, String ecoContent, int ecoTime, int ecoPoints) {
        this.ecoId = ecoId;
        this.userId = userId;
        this.ecoName = ecoName;
        this.ecoContent = ecoContent;
        this.ecoTime = ecoTime;
        this.ecoPoints = ecoPoints;
    }

    // Getters and setters
    public String getEcoId() { return ecoId; }
    public void setEcoId(String ecoId) { this.ecoId = ecoId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEcoName() { return ecoName; }
    public void setEcoName(String ecoName) { this.ecoName = ecoName; }
    public String getEcoContent() { return ecoContent; }
    public void setEcoContent(String ecoContent) { this.ecoContent = ecoContent; }
    public int getEcoTime() { return ecoTime; }
    public void setEcoTime(int ecoTime) { this.ecoTime = ecoTime; }
    public int getEcoPoints() { return ecoPoints; }
    public void setEcoPoints(int ecoPoints) { this.ecoPoints = ecoPoints; }
}
