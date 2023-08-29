package org.xsk.domain.calendar;

import org.xsk.domain.calendar.exception.NoCompatibleRuleTypeException;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Entity;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CalendarRule extends Entity<CalendarRuleCode> {
    CalendarRuleCode code;
    Map<LocalDate, DaySubRule> daySubRules;

    public CalendarRule(CalendarRuleCode code, Map<LocalDate, DaySubRule> daySubRules) {
        this.code = code;
        this.daySubRules = daySubRules;
        validSpecification();
    }

    public List<Map.Entry<RuleAppliedDay, Boolean>> produceDayRuleResult(NatureDay natureDayBegin, NatureDay natureDayEnd) {
        List<RuleAppliedDay> ruleAppliedDays = applyRule(natureDayBegin, natureDayEnd);
        return ruleAppliedDays.stream()
                .map(ruleAppliedDay -> new AbstractMap.SimpleEntry<>(ruleAppliedDay, ruleAppliedDay.isWorkDay()))
                .collect(Collectors.toList());
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
        validSpecification();
    }


    @Override
    public CalendarRuleCode id() {
        return code;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnCondition(CalendarRule.this.code == null, "calendar rule code date cant be null");
                throwOnCondition(CalendarRule.this.daySubRules == null, "calendar rule day rule cant be null");
            }
        };
    }
}
