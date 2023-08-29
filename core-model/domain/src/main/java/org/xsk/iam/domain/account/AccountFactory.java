package org.xsk.iam.domain.account;

import lombok.AllArgsConstructor;
import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainFactory;
import org.xsk.domain.common.EventBus;
import org.xsk.iam.domain.account.event.MainAcctCreatedEvent;
import org.xsk.iam.domain.app.AppCode;
import org.xsk.iam.domain.app.AppConfigService;
import org.xsk.iam.domain.app.TenantCode;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.role.RoleValidateService;
import org.xsk.iam.domain.site.SiteCode;
import org.xsk.iam.domain.site.SiteConfigService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class AccountFactory extends DomainFactory {
    AppConfigService appConfigService;
    SiteConfigService siteConfigService;
    AccountRepository accountRepository;
    AcctUniquenessValidateService acctUniquenessValidateService;
    RoleValidateService roleValidateService;

    public Account createMainAcct(AppCode appCode, TenantCode tenantCode, SiteCode curSite, Uid mainAcctUid, Credential credential,
                                  String nickname, Avatar avatar, Region region, boolean needResetPassword, Map<String, Object> extraProps,
                                  Set<RoleCode> roles, Lang lang) {
        roleValidateService.validateCodes(roles);
        String mainAcctDomain = appConfigService.restoreAppDomain(appCode);
        AppUidUniqueKey mainAcctUniqKey = new AppUidUniqueKey(
                appCode,
                Code.isEmptyVal(mainAcctUid) ? Uid.randomUid() : mainAcctUid
        );
        Uid parentUid = Uid.emptyUid();
        Map<String, Object> preference = siteConfigService.restoreSiteConfig(curSite, "default.preference", new HashMap<>());
        acctUniquenessValidateService.validateAccountUniqueness(mainAcctUniqKey, tenantCode, parentUid, mainAcctDomain, credential);
        Account account = new Account(
                mainAcctUniqKey, tenantCode, parentUid, mainAcctDomain, Collections.singleton(curSite),
                credential, AcctStatus.NORMAL, nickname, avatar, region,
                needResetPassword, extraProps, new AcctActivityRecord(),
                Collections.singleton(new AcctSiteProfile(curSite, roles, lang, preference))
        );
        EventBus.fire(new MainAcctCreatedEvent(mainAcctUniqKey));
        return account;
    }


}
