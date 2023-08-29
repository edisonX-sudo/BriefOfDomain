package org.xsk.iam.domain.app;

import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainSpecificationValidator;


public class AppCode extends Code<String> {

    public AppCode(String code) {
        super(code);
        validSpecification();
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnEmpty(value(), "appCode is empty");
                throwOnGt(value(), 24, "appCode length must be less than 24");
            }
        };
    }


}
