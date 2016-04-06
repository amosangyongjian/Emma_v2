package com.example.amosang.emma_v2;

import java.util.Date;

/**
 * Created by amosang on 21/03/16.
 */
public class UserReq {
    private String requestTitle;
    private Date requestDate;
    private String audioPath;

    public UserReq(String requestTitle, Date requestDate, String audioPath) {
        this.requestTitle = requestTitle;
        this.requestDate = requestDate;
        this.audioPath = audioPath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
