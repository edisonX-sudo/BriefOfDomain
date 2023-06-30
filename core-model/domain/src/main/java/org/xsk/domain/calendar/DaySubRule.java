package org.xsk.domain.calendar;

import org.xsk.domain.common.ValueObject;

import java.time.LocalDate;

public class DaySubRule extends ValueObject {
    LocalDate date;
    DaySubRuleType ruleType;
    String description;

    public DaySubRule(LocalDate date, DaySubRuleType ruleType, String description) {
        this.date = date;
        this.ruleType = ruleType;
        this.description = description;
    }
}
