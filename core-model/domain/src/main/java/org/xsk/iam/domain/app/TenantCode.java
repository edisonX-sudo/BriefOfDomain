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
        return throwIllegalStateException -> {
            throwIllegalStateException.accept(StrUtil.isEmpty(value()), "appCode is empty");
            throwIllegalStateException.accept(value().length() >= 24, "appCode length must be less than 24");
        };
    }
}
