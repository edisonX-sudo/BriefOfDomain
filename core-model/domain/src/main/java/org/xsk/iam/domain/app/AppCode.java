package org.xsk.iam.domain.app;

import cn.hutool.core.util.StrUtil;
import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainSpecificationValidator;


public class AppCode extends Code<String> {

    public AppCode(String code) {
        super(code);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnCondition(StrUtil.isEmpty(AppCode.this.value()), "appCode is empty");
                throwOnCondition(AppCode.this.value().length() >= 24, "appCode length must be less than 24");
            }
        };
    }


}
