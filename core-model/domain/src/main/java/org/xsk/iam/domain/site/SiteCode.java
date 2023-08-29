package org.xsk.iam.domain.site;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.UniqueKey;

public class SiteCode extends UniqueKey<String> {
    public SiteCode(String id) {
        super(id);
        validSpecification();
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnEmpty(value(),defaultThrowMsg("siteCode"));
            }
        };
    }
}
