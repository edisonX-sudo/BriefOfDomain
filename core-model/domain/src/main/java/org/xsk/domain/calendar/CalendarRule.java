package org.xsk.domain.calendar;

import org.xsk.domain.calendar.exception.NoCompatibleRuleTypeException;
import org.xsk.domain.common.Entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CalendarRule extends Entity<CalendarRuleCode> {
    private CalendarRuleCode code;
    public Map<LocalDate, DaySubRule> daySubRules;
    private boolean isNew;

    public CalendarRule(CalendarRuleCode code, Map<LocalDate, DaySubRule> daySubRules, boolean isNew) {
        this.code = code;
        this.daySubRules = daySubRules;
        this.isNew = isNew;
    }

    public Map<RuleAppliedDay, Boolean> produceDayRuleResult(NatureDay natureDayBegin, NatureDay natureDayEnd) {
        List<RuleAppliedDay> ruleAppliedDays = applyRule(natureDayBegin, natureDayEnd);
        return ruleAppliedDays.stream()
                .collect(
                        Collectors.toMap(
                                ruleAppliedDay -> ruleAppliedDay,
                                RuleAppliedDay::isWorkDay,
                                (aBoolean, aBoolean2) -> aBoolean
                        )
                );
    }

    List<RuleAppliedDay> applyRule(NatureDay natureDayBegin, NatureDay natureDayEnd) {
        List<NatureDay> rangeNatureDays = NatureDay.assembleRangeDays(natureDayBegin, natureDayEnd);
        return rangeNatureDays.stream().map(this::applyRule).collect(Collectors.toList());
    }

    RuleAppliedDay applyRule(NatureDay natureDay) {
        DaySubRule daySubRule = daySubRules.get(natureDay.date);
        if (daySubRule == null) {
            return new RuleAppliedDay(natureDay, RuleAppliedDayType.NATURE_DAY);
        } else {
            switch (daySubRule.ruleType) {
                case TO_REST_DAY:
                    return new RuleAppliedDay(natureDay, RuleAppliedDayType.TO_REST_DAY);
                case TO_WORK_DAY:
                    return new RuleAppliedDay(natureDay, RuleAppliedDayType.TO_WORK_DAY);
            }
            throw new NoCompatibleRuleTypeException();
        }
    }

    public void update(Set<DaySubRule> daySubRules) {
        this.daySubRules = DaySubRule.assembleDateRuleMap(daySubRules);
    }



    @Override
    protected Boolean isNew() {
        return isNew;
    }

    @Override
    public CalendarRuleCode id() {
        return code;
    }


}
