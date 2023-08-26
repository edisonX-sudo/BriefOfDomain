package org.xsk.iam.domain.account;

import org.xsk.domain.common.Code;
import org.xsk.domain.common.EventBus;
import org.xsk.iam.domain.account.event.MainAcctCreatedEvent;
import org.xsk.iam.domain.app.AppCode;
import org.xsk.iam.domain.app.AppConfigService;
import org.xsk.iam.domain.app.TenantCode;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class AccountFactory {
    AppConfigService appConfigService;

    public Account createMainAcct(AppCode appCode, TenantCode tenantCode, SiteCode curSite, Uid mainAcctUid, Credential credential,
                                  String nickname, Avatar avatar, Region region, boolean needResetPassword, Map<String, Object> extraProps,
                                  Set<RoleCode> roles, Map<String, Object> preference) {
        String domain = appConfigService.restoreAppDomain(appCode);
        AppUidUniqueKey mainAcctUniqKey = new AppUidUniqueKey(
                appCode,
                Code.isEmptyVal(mainAcctUid) ? Uid.randomeUid() : mainAcctUid
        );
        Account account = new Account(
                mainAcctUniqKey, tenantCode, Uid.emptyUid(), domain, Collections.singleton(curSite),
                credential, AcctStatus.Normal, nickname, avatar, region,
                needResetPassword, extraProps, new AcctActivityRecord(),
                Collections.singleton(new AccountSiteProfile(curSite, roles, preference))
        );
        EventBus.fire(new MainAcctCreatedEvent(mainAcctUniqKey));
        return account;
    }
}
