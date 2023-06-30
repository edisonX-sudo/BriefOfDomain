package org.xsk.domain.calendar;

import org.xsk.domain.common.ValueObject;

import java.time.LocalDate;

public class RuleAppliedDay extends ValueObject {
    NatureDay natureDay;
    RuleAppliedDayType type;

    public RuleAppliedDay(NatureDay natureDay, RuleAppliedDayType type) {
        this.natureDay = natureDay;
        this.type = type;
        new Validator.RuleAppliedDaySpecificationValidator(this).validSpecification();
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

}
