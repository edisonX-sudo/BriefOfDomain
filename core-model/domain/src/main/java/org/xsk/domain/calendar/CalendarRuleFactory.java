package org.xsk.domain.calendar;

import org.xsk.domain.common.DomainFactory;

import java.util.Set;

public abstract class CalendarRuleFactory extends DomainFactory {

    public CalendarRule build(CalendarRuleCode code, Set<DaySubRule> daySubRules) {
        return new CalendarRule(code, DaySubRule.assembleDateRuleMap(daySubRules), true);
    }

}
