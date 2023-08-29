package org.xsk.domain.calendar;

import cn.hutool.core.util.StrUtil;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DaySubRule extends ValueObject {
    LocalDate date;
    DaySubRuleType ruleType;
    String description;

    public DaySubRule(LocalDate date, DaySubRuleType ruleType, String description) {
        this.date = date;
        this.ruleType = ruleType;
        this.description = description;
        validSpecification();
    }

    static Map<LocalDate, DaySubRule> assembleDateRuleMap(Set<DaySubRule> daySubRules) {
        return daySubRules.stream()
                .collect(
                        Collectors.toMap(
                                daySubRule -> daySubRule.date,
                                daySubRule -> daySubRule,
                                (daySubRule, daySubRule2) -> daySubRule)
                );
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnCondition(DaySubRule.this.date == null, "day sub rule cant be null");
                throwOnCondition(DaySubRule.this.ruleType == null, "day rule type cant be null");
                throwOnCondition(StrUtil.length(DaySubRule.this.description) > 50, "day rule description length over limit");
            }
        };
    }
}
