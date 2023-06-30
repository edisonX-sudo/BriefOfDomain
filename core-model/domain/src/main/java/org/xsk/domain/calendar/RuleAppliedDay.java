package org.xsk.domain.calendar;

import org.xsk.domain.common.ValueObject;

public class RuleAppliedDay extends ValueObject {
    NatureDay natureDay;
    RuleAppliedDayType type;

    public RuleAppliedDay(NatureDay natureDay, RuleAppliedDayType type) {
        this.natureDay = natureDay;
        this.type = type;
    }

    Boolean isWorkDay() {
        if (type.applied) {
            return type == RuleAppliedDayType.TO_WORK_DAY;
        } else {
            return natureDay.isWorkDay();
        }
    }
}
