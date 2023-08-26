package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

public class Avatar extends ValueObject {
    String avatarKey;
    String avatarUrl;

    public Avatar(String avatarKey, String avatarUrl) {
        this.avatarKey = avatarKey;
        this.avatarUrl = avatarUrl;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }
}
