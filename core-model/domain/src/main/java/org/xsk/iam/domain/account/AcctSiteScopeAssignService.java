package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.site.SiteCode;
import org.xsk.iam.domain.site.SiteConfigService;

import java.util.*;
import java.util.stream.Collectors;

public class AcctSiteScopeAssignService extends DomainService {
    SiteConfigService siteConfigService;

    void assignSiteScope(Account account, Set<SiteCode> siteCodes, Lang lang) {
        HashSet<SiteCode> siteScope = new HashSet<>(account.siteScope);
        siteScope.addAll(siteCodes);
        account.siteScope = siteScope;

        Set<AccountSiteProfile> assignedSiteProfile = siteScope.stream()
                .map(siteCode -> {
                    Map<String, Object> preference = siteConfigService.restoreSiteConfig(siteCode, "default.preference", new HashMap<>());
                    return new AccountSiteProfile(siteCode, Collections.emptySet(), lang, preference);
                })
                .collect(Collectors.toSet());
        HashSet<AccountSiteProfile> accountSiteProfiles = new HashSet<>(account.accountSiteProfiles);
        accountSiteProfiles.addAll(assignedSiteProfile);
        account.accountSiteProfiles = accountSiteProfiles;
    }

    void removeSiteScope(Account account, Set<SiteCode> siteCodes) {
        HashSet<SiteCode> siteScope = new HashSet<>(account.siteScope);
        siteScope.removeAll(siteCodes);
        account.siteScope = siteScope;

        HashSet<AccountSiteProfile> accountSiteProfiles = new HashSet<>(account.accountSiteProfiles);
        account.accountSiteProfiles = accountSiteProfiles.stream()
                .filter(accountSiteProfile -> siteCodes.contains(accountSiteProfile.siteCode))
                .collect(Collectors.toSet());
    }
}
