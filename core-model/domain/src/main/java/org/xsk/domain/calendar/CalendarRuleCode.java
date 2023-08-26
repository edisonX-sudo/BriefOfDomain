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
        return (throwIllegalStateException) -> {
            throwIllegalStateException.accept(StrUtil.isEmpty(this.value()), "rule code cant be empty");
        };
    }
}
