package org.xsk.iam.domain.account;

import lombok.AllArgsConstructor;
import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainService;
import org.xsk.domain.common.EventBus;
import org.xsk.iam.domain.account.event.SubAcctCreatedEvent;
import org.xsk.iam.domain.account.exception.SubAcctCantCreateAcctException;
import org.xsk.iam.domain.app.AppCode;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;
import org.xsk.iam.domain.site.SiteConfigService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class SubAcctCreateService extends DomainService {
    SiteConfigService siteConfigService;

    Account createSubAcct(Account mainAcct, SiteCode curSite, Uid subAcctUid, Credential credential,
                          String nickname, Avatar avatar, Region region, Map<String, Object> extraProps,
                          Set<RoleCode> roles, Lang lang) {
        if (!mainAcct.isMainAcct()) {
            throw new SubAcctCantCreateAcctException();
        }
        AppCode mainAcctAppCode = mainAcct.appUidKey.value().appCode();
        AppUidUniqueKey subAcctUniqKey = new AppUidUniqueKey(
                mainAcctAppCode,
                Code.isEmptyVal(subAcctUid) ? Uid.randomeUid() : subAcctUid
        );
        Uid mainAcctUid = mainAcct.appUidKey.value().uid();
        String domain = siteConfigService.restoreSiteDomain(curSite);
        Map<String, Object> preference = siteConfigService.restoreSiteConfig(curSite, "default.preference", new HashMap<>());
        Account account = new Account(
                subAcctUniqKey, mainAcct.tenantCode, mainAcctUid, domain, Collections.singleton(curSite),
                credential, AcctStatus.NOT_ACTIVE, nickname, avatar, region,
                true, extraProps, new AcctActivityRecord(),
                Collections.singleton(new AccountSiteProfile(curSite, roles, lang, preference))
        );
        EventBus.fire(new SubAcctCreatedEvent(subAcctUniqKey));
        return account;
    }

}
