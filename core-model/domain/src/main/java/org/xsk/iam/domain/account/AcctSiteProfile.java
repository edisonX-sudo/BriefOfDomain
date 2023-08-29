package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;

import java.util.Map;
import java.util.Set;

public class AcctSiteProfile extends ValueObject {
    private static final String LANGUAGE = "language";
    SiteCode siteCode;
    Set<RoleCode> roleCodes;
    Lang lang;
    Map<String, Object> preference;


    public AcctSiteProfile(SiteCode siteCode, Set<RoleCode> roleCodes, Lang lang, Map<String, Object> preference) {
        this.siteCode = siteCode;
        this.roleCodes = roleCodes;
        this.lang = lang;
        this.preference = preference;
        init(lang);
        validSpecification();
    }

    private void init(Lang lang) {
        this.preference.put(LANGUAGE, lang.name());
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnNull(siteCode, defaultThrowMsg("siteCode"));
                throwOnNull(roleCodes, defaultThrowMsg("roleCodes"));
                throwOnNull(lang, defaultThrowMsg("lang"));
                throwOnNull(preference, defaultThrowMsg("preference"));
            }
        };
    }
}
