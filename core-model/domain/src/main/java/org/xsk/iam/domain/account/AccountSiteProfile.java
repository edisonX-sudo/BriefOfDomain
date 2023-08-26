package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;

import java.util.Map;
import java.util.Set;

public class AccountSiteProfile extends ValueObject {
    SiteCode siteCode;
    Set<RoleCode> roleCodes;
    Lang lang;
    Map<String, Object> preference;


    public AccountSiteProfile(SiteCode siteCode, Set<RoleCode> roleCodes, Lang lang, Map<String, Object> preference) {
        this.siteCode = siteCode;
        this.roleCodes = roleCodes;
        this.lang = lang;
        this.preference = preference;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }
}
