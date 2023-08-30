package org.xsk.domain.activity;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

import java.time.LocalDateTime;

public class ActivityDuration extends ValueObject {
    LocalDateTime startTime;
    LocalDateTime endTime;

    public ActivityDuration(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        validSpecification();
    }

    public boolean isNowInDuration() {
        LocalDateTime now = LocalDateTime.now();
        return startTime.isBefore(now) && endTime.isAfter(now);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnNull(startTime, defaultThrowMsg("startTime"));
                throwOnNull(endTime, defaultThrowMsg("endTime"));
                throwOnCondition(startTime.isAfter(endTime), "startTime must before endTime");
            }
        };
    }
}
