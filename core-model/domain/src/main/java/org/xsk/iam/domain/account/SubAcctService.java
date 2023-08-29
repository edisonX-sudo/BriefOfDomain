package org.xsk.iam.domain.account;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainService;
import org.xsk.domain.common.EventBus;
import org.xsk.iam.domain.account.event.SubAcctCreatedEvent;
import org.xsk.iam.domain.account.exception.*;
import org.xsk.iam.domain.app.AppCode;
import org.xsk.iam.domain.app.TenantCode;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;
import org.xsk.iam.domain.site.SiteConfigService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class SubAcctService extends DomainService {
    SiteConfigService siteConfigService;
    AccountRepository accountRepository;

    Account createSubAcct(Account mainAcct, SiteCode curSite, Uid subAcctUid, Credential credential,
                          String nickname, Avatar avatar, Region region, Map<String, Object> extraProps,
                          Set<RoleCode> roles, Lang lang) {
        //createSubAcct是包级方法,不用检查Account的存在(这个包owner会把控调用的上下文)
        throwOnCondition(!mainAcct.isMainAcct(), new OnlyMainAcctCanOperateException());
        String subAcctSiteDomain = siteConfigService.restoreSiteDomain(curSite);
        AppUidUniqueKey mainAcctAppUidKey = mainAcct.appUidKey;
        throwOnCondition(accountRepository.countSiteSubAcct(mainAcctAppUidKey, subAcctSiteDomain) > 1000, new SubAcctCountOverLimit());
        TenantCode tenantCode = mainAcct.tenantCode;
        Uid subAcctParentUid = mainAcctAppUidKey.uid();
        throwOnCondition(accountRepository.existUid(mainAcctAppUidKey, tenantCode), new AcctUidExistException());
        throwOnCondition(StrUtil.isNotEmpty(credential.loginName)
                        && accountRepository.existLoginName(mainAcctAppUidKey, tenantCode, subAcctParentUid, subAcctSiteDomain, credential.loginName),
                new AcctLoginNameExistException());
        throwOnCondition(StrUtil.isNotEmpty(credential.email)
                        && accountRepository.existEmail(mainAcctAppUidKey, tenantCode, subAcctParentUid, subAcctSiteDomain, credential.email),
                new AcctEmailExistException());
        throwOnCondition(StrUtil.isNotEmpty(credential.mobile)
                        && accountRepository.existMobile(mainAcctAppUidKey, tenantCode, subAcctParentUid, subAcctSiteDomain, credential.mobile),
                new AcctMobileExistException());
        AppUidUniqueKey subAcctUniqKey = new AppUidUniqueKey(
                mainAcctAppUidKey.appCode(),
                Code.isEmptyVal(subAcctUid) ? Uid.randomeUid() : subAcctUid
        );
        Map<String, Object> preference = siteConfigService.restoreSiteConfig(curSite, "default.preference", new HashMap<>());
        Account subAccount = new Account(
                subAcctUniqKey, tenantCode, subAcctParentUid, subAcctSiteDomain, Collections.singleton(curSite),
                credential, AcctStatus.NOT_ACTIVE, nickname, avatar, region,
                true, extraProps, new AcctActivityRecord(),
                Collections.singleton(new AccountSiteProfile(curSite, roles, lang, preference))
        );
        EventBus.fire(new SubAcctCreatedEvent(subAcctUniqKey));
        return subAccount;
    }

    void deleteSubAcct(Account mainAcct, Account subAcct) {
        //deleteSubAcct是pub级方法,要检查Account的存在,因为外部会调用它
        if (subAcct == null) {
            throw new AcctNotFoundException();
        }
        if (!mainAcct.isMainAcct()) {
            throw new OnlyMainAcctCanOperateException();
        }
        AppCode mainAcctAppCode = mainAcct.appUidKey.appCode();
        AppCode subAcctAppCode = subAcct.appUidKey.appCode();
        Uid mainAcctUid = mainAcct.appUidKey.uid();
        Uid subAcctParentUid = subAcct.parentUid;
        if (!subAcctAppCode.equals(mainAcctAppCode) || !subAcctParentUid.equals(mainAcctUid)) {
            throw new OnlyParentAcctCanOperateException();
        }
        accountRepository.softDelete(subAcct.appUidKey);
    }

}
