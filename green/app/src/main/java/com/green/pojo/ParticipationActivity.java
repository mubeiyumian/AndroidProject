package com.green.pojo;

import java.util.UUID;

public class ParticipationActivity {
    private String pId;
    private String aId;
    private String userId;

    public static String generatePId() {
        return UUID.randomUUID().toString();
    }

    // Getters and setters
    public String getpId() { return pId; }
    public void setpId(String pId) { this.pId = pId; }
    public String getaId() { return aId; }
    public void setaId(String aId) { this.aId = aId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
