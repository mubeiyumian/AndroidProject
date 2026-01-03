package com.green.pojo;

import java.io.Serializable;

public class Knowledge implements Serializable {
    private String kId;
    private String kName;
    private String kContent;

    // Getters and setters
    public String getkId() { return kId; }
    public void setkId(String kId) { this.kId = kId; }
    public String getkName() { return kName; }
    public void setkName(String kName) { this.kName = kName; }
    public String getkContent() { return kContent; }
    public void setkContent(String kContent) { this.kContent = kContent; }
}
