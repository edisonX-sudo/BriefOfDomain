package org.xsk.domain.calendar;

import org.xsk.domain.common.Id;

public class CalendarRuleCode extends Id<String> {
    public CalendarRuleCode(String id) {
        super(id);
        new Validator.CalendarRuleCodeSpecificationValidator(this).validSpecification();
    }
}
