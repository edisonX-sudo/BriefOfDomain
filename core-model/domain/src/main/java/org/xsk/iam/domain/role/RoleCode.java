package org.xsk.iam.domain.role;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.UniqueKey;

public class RoleCode extends UniqueKey<String> {

    public RoleCode(String id) {
        super(id);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }
}
