package com.atypon.domain;

public class UserLicence {
    private String username;
    private ContentLicence contentLicence;
    private long startDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public ContentLicence getContentLicence() {
        return contentLicence;
    }

    public void setContentLicence(ContentLicence contentLicence) {
        this.contentLicence = contentLicence;
    }
}
