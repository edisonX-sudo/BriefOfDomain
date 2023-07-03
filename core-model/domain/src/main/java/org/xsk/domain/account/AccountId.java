package org.xsk.domain.account;

import org.xsk.domain.common.AggregateComponent;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Id;

public class AccountId extends Id<Long> {
    public AccountId(Long id) {
        super(id);
    }

    @Override
    protected DomainSpecificationValidator<? extends AggregateComponent>  specificationValidator() {
        return null;
    }
}
