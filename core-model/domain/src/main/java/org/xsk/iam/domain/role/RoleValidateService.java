package org.xsk.iam.domain.role;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.site.SiteCode;

import java.util.Set;

public class RoleValidateService extends DomainService {
    public void validateCodes(SiteCode curSite, Set<RoleCode> roles) {
        // TODO: 2023/8/29 校验curSite下是否存在roles
    }
}
