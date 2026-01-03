package com.green.pojo;

import java.io.Serializable;

public class OnlineActivity implements Serializable {
    private String activityId;
    private String organization;
    private String remainingSlots;
    private String activityName;
    private String date;

    // Getters and setters
    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }
    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }
    public String getRemainingSlots() { return remainingSlots; }
    public void setRemainingSlots(String remainingSlots) { this.remainingSlots = remainingSlots; }
    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
