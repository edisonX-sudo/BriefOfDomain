package org.xsk.domain.activity;

import java.time.LocalDateTime;

public class ActivityDuration {
    LocalDateTime startTime;
    LocalDateTime endTime;

    public ActivityDuration(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isNowInDuration(){
        LocalDateTime now = LocalDateTime.now();
        return startTime.isBefore(now) && endTime.isAfter(now);
    }
}
