package org.xsk.domain.calendar;

import org.xsk.domain.common.AggregateComponent;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Id;

public class CalendarRuleCode extends Id<String> {
    public CalendarRuleCode(String id) {
        super(id);
        validSpecification();
    }

    @Override
    protected DomainSpecificationValidator<? extends AggregateComponent>  specificationValidator() {
        return new Validator.CalendarRuleCodeSpecificationValidator(this);
    }
}
