package org.xsk.domain.device;

import org.xsk.domain.common.AggregateComponent;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Id;

public class DeviceId extends Id<Long> {
    public DeviceId(Long id) {
        super(id);
    }

    @Override
    protected DomainSpecificationValidator<? extends AggregateComponent> specificationValidator() {
        return null;
    }
}
