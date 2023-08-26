package org.xsk.iam.domain.account;

import cn.hutool.core.lang.id.NanoId;
import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainSpecificationValidator;

public class Uid extends Code<String> {

    public Uid(String code) {
        super(code);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }

    public static Uid randomeUid(){
        return new Uid(NanoId.randomNanoId(18));
    }

    public static Uid emptyUid(){
        return new Uid("");
    }
}
