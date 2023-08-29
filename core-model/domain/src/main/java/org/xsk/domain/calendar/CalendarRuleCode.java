package org.xsk.domain.calendar;

import cn.hutool.core.util.StrUtil;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.UniqueKey;

public class CalendarRuleCode extends UniqueKey<String> {
    public CalendarRuleCode(String id) {
        super(id);
        validSpecification();
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnCondition(StrUtil.isEmpty(CalendarRuleCode.this.value()), "rule code cant be empty");
            }
        };
    }
}
