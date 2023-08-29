package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.account.exception.OnlyMainAcctCanOperateException;
import org.xsk.iam.domain.site.SiteCode;
import org.xsk.iam.domain.site.SiteConfigService;

import java.util.*;
import java.util.stream.Collectors;

public class AcctSiteScopeAssignService extends DomainService {
    SiteConfigService siteConfigService;

    void assignSiteScope(Account account, Set<SiteCode> siteCodes, Lang lang) {
        if(!account.isMainAcct()){
            throw new OnlyMainAcctCanOperateException();
        }
        HashSet<SiteCode> siteScope = new HashSet<>(account.siteScope);
        siteScope.addAll(siteCodes);
        account.siteScope = siteScope;

        Set<AcctSiteProfile> assignedSiteProfile = siteCodes.stream()
                .map(siteCode -> {
                    Map<String, Object> preference = siteConfigService.restoreSiteConfig(siteCode, "default.preference", new HashMap<>());
                    return new AcctSiteProfile(siteCode, Collections.emptySet(), lang, preference);
                })
                .collect(Collectors.toSet());
        HashSet<AcctSiteProfile> acctSiteProfiles = new HashSet<>(account.acctSiteProfiles);
        acctSiteProfiles.addAll(assignedSiteProfile);
        account.acctSiteProfiles = acctSiteProfiles;
    }

    void removeSiteScope(Account account, Set<SiteCode> siteCodes) {
        if(!account.isMainAcct()){
            throw new OnlyMainAcctCanOperateException();
        }
        HashSet<SiteCode> siteScope = new HashSet<>(account.siteScope);
        siteScope.removeAll(siteCodes);
        account.siteScope = siteScope;

        HashSet<AcctSiteProfile> acctSiteProfiles = new HashSet<>(account.acctSiteProfiles);
        account.acctSiteProfiles = acctSiteProfiles.stream()
                .filter(acctSiteProfile -> !siteCodes.contains(acctSiteProfile.siteCode))
                .collect(Collectors.toSet());
    }
}
