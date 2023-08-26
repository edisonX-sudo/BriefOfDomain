package org.xsk.iam.domain.site;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.UniqueKey;

public class SiteCode extends UniqueKey<String> {
    public SiteCode(String id) {
        super(id);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }
}
