package org.xsk.domain.activity;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

import java.time.LocalDateTime;

/**
 * 活动开始结束时间
 */
public class ActivityDuration extends ValueObject {
    LocalDateTime startTime;
    LocalDateTime endTime;

    public ActivityDuration(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        validSpecification();
    }

    public boolean isNowInDuration() {
        //专门设计的vo有更强的内聚性,其中方法对本身字段的使用率更高,
        // 符合高内聚的评判标准,对复杂度有更强的限制
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
