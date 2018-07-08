package com.vertx.scheduler.example;

public class ScheduleDetails {
    private String url;
    private String timeInSec;


    public ScheduleDetails() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeInSec() {
        return timeInSec;
    }

    public void setTimeInSec(String timeInSec) {
        this.timeInSec = timeInSec;
    }
}
