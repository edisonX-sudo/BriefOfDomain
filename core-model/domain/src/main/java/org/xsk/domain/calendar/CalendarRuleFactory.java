package org.xsk.domain.calendar;

import org.xsk.domain.common.DomainFactory;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CalendarRuleFactory extends DomainFactory {

    public CalendarRule build(CalendarRuleCode code, Set<DaySubRule> daySubRules) {
        return new CalendarRule(code, assembleDaySubRulesMap(daySubRules), true);
    }

    static Map<LocalDate, DaySubRule> assembleDaySubRulesMap(Set<DaySubRule> daySubRules) {
        return daySubRules
                .stream()
                .collect(
                        Collectors.toMap(
                                daySubRule -> daySubRule.date,
                                daySubRule -> daySubRule,
                                (daySubRule, daySubRule2) -> daySubRule)
                );
    }

}
