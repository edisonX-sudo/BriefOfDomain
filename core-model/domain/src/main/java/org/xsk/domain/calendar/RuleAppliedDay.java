package org.xsk.domain.calendar;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

import java.time.LocalDate;

public class RuleAppliedDay extends ValueObject {
    NatureDay natureDay;
    RuleAppliedDayType type;

    public RuleAppliedDay(NatureDay natureDay, RuleAppliedDayType type) {
        this.natureDay = natureDay;
        this.type = type;
        validSpecification();
    }

    Boolean isWorkDay() {
        if (type.applied) {
            return type == RuleAppliedDayType.TO_WORK_DAY;
        } else {
            return natureDay.isWorkDay();
        }
    }

    public LocalDate date() {
        return natureDay.date;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return (throwIllegalStateException) -> {
            throwIllegalStateException.accept(this.natureDay == null, "rule applied day natureDay cant be null");
            throwIllegalStateException.accept(this.type == null, "rule applied day type cant be null");
        };
    }

}
