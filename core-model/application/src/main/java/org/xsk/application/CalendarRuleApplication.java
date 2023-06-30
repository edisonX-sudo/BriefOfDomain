package org.xsk.application;

import org.xsk.domain.calendar.*;
import org.xsk.domain.common.DomainApplication;

import java.util.Map;
import java.util.Set;

public class CalendarRuleApplication extends DomainApplication {
    CalendarRuleFactory calendarRuleFactory;
    CalendarRuleRepo calendarRuleRepo;

    public CalendarRuleCode create(CalendarRuleCode code, Set<DaySubRule> daySubRules) {
        CalendarRule build = calendarRuleFactory.build(code, daySubRules);
        calendarRuleRepo.save(build);
        return code;
    }

    public void update(CalendarRuleCode code, Set<DaySubRule> daySubRules) {
        CalendarRule calendarRule = calendarRuleRepo.findNotNone(code);
        calendarRule.update(daySubRules);
        calendarRuleRepo.save(calendarRule);
    }

    public Map<RuleAppliedDay, Boolean> produceDayRuleResult(CalendarRuleCode code, NatureDay natureDayBegin, NatureDay natureDayEnd) {
        CalendarRule calendarRule = calendarRuleRepo.findNotNone(code);
        return calendarRule.produceDayRuleResult(natureDayBegin, natureDayEnd);
    }

    public void delete(CalendarRuleCode calendarRuleCode) {
        //下面可查可不查,看需求
        CalendarRule calendarRule = calendarRuleRepo.findNotNone(calendarRuleCode);
        calendarRuleRepo.delete(calendarRule.id());
    }
}
