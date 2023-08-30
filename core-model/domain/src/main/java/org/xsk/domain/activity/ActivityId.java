package org.xsk.domain.activity;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Id;

public class ActivityId extends Id<Long> {

    public ActivityId(Long id) {
        super(id);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }
}
