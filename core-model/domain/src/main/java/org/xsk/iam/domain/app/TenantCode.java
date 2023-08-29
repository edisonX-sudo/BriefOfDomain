package org.xsk.iam.domain.app;

import cn.hutool.core.util.StrUtil;
import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainSpecificationValidator;

public class TenantCode extends Code<String> {

    public TenantCode(String code) {
        super(code);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnCondition(StrUtil.isEmpty(TenantCode.this.value()), "appCode is empty");
                throwOnCondition(TenantCode.this.value().length() >= 24, "appCode length must be less than 24");
            }
        };
    }
}
