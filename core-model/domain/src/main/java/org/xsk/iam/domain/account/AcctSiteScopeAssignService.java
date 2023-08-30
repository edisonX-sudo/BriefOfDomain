package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.account.exception.OnlyMainAcctCanOperateException;
import org.xsk.iam.domain.site.SiteCode;
import org.xsk.iam.domain.site.SiteConfigService;

import java.util.*;
import java.util.stream.Collectors;

public class AcctSiteScopeAssignService extends DomainService {
    SiteConfigService siteConfigService;

    void assignSiteScope(IamAccount iamAccount, Set<SiteCode> siteCodes, Lang lang) {
        if(!iamAccount.isMainAcct()){
            throw new OnlyMainAcctCanOperateException();
        }
        HashSet<SiteCode> siteScope = new HashSet<>(iamAccount.siteScope);
        siteScope.addAll(siteCodes);
        iamAccount.siteScope = siteScope;

        Set<AcctSiteProfile> assignedSiteProfile = siteCodes.stream()
                .map(siteCode -> {
                    Map<String, Object> preference = siteConfigService.restoreSiteConfig(siteCode, "default.preference", new HashMap<>());
                    return new AcctSiteProfile(siteCode, Collections.emptySet(), lang, preference);
                })
                .collect(Collectors.toSet());
        HashSet<AcctSiteProfile> acctSiteProfiles = new HashSet<>(iamAccount.acctSiteProfiles);
        acctSiteProfiles.addAll(assignedSiteProfile);
        iamAccount.acctSiteProfiles = acctSiteProfiles;
    }

    void removeSiteScope(IamAccount iamAccount, Set<SiteCode> siteCodes) {
        if(!iamAccount.isMainAcct()){
            throw new OnlyMainAcctCanOperateException();
        }
        HashSet<SiteCode> siteScope = new HashSet<>(iamAccount.siteScope);
        siteScope.removeAll(siteCodes);
        iamAccount.siteScope = siteScope;

        HashSet<AcctSiteProfile> acctSiteProfiles = new HashSet<>(iamAccount.acctSiteProfiles);
        iamAccount.acctSiteProfiles = acctSiteProfiles.stream()
                .filter(acctSiteProfile -> !siteCodes.contains(acctSiteProfile.siteCode))
                .collect(Collectors.toSet());
    }
}
