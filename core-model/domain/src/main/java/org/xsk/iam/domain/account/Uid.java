package org.xsk.iam.domain.account;

import cn.hutool.core.lang.id.NanoId;
import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainSpecificationValidator;

public class Uid extends Code<String> {

    public Uid(String code) {
        super(code);
        validSpecification();
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnGt(value(), 24, defaultThrowMsg("uid"));
            }
        };
    }

    public static Uid randomeUid() {
        return new Uid(NanoId.randomNanoId(18));
    }

    public static Uid emptyUid() {
        return new Uid("");
    }
}
