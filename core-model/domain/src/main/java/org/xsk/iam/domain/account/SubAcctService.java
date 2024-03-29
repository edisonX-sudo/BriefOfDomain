package org.xsk.iam.domain.account;

import lombok.AllArgsConstructor;
import org.xsk.domain.common.DomainService;
import org.xsk.domain.common.EventBus;
import org.xsk.iam.domain.account.event.SubAcctCreatedEvent;
import org.xsk.iam.domain.account.exception.AcctNotFoundException;
import org.xsk.iam.domain.account.exception.OnlyMainAcctCanOperateException;
import org.xsk.iam.domain.account.exception.OnlyParentAcctCanOperateException;
import org.xsk.iam.domain.account.exception.SubAcctCountOverLimit;
import org.xsk.iam.domain.app.AppCode;
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
public class SubAcctService extends DomainService {
    SiteConfigService siteConfigService;
    IamAccountRepository iamAccountRepository;
    RoleValidateService roleValidateService;
    AcctUniquenessValidateService acctUniquenessValidateService;

    IamAccount createSubAcct(IamAccount mainAcct, SiteCode curSite, Uid subAcctUid, Credential credential,
                             String nickname, Avatar avatar, Region region, Map<String, Object> extraProps,
                             Set<RoleCode> roles, Lang lang) {
        //createSubAcct是包级方法,不用检查Account的存在(这个包owner会把控调用的上下文)
        if (!mainAcct.isMainAcct())
            throw new OnlyMainAcctCanOperateException();
        roleValidateService.validateCodes(curSite,roles);
        String subAcctSiteDomain = siteConfigService.restoreSiteDomain(curSite);
        AppUidUniqueKey mainAcctAppUidKey = mainAcct.appUidKey;
        if (iamAccountRepository.countSiteSubAcct(mainAcctAppUidKey, subAcctSiteDomain) > 1000)
            throw new SubAcctCountOverLimit();
        TenantCode tenantCode = mainAcct.tenantCode;
        Uid subAcctParentUid = mainAcctAppUidKey.uid();
        acctUniquenessValidateService.validateAccountUniqueness(mainAcctAppUidKey, tenantCode, subAcctParentUid, subAcctSiteDomain, credential);
        AppUidUniqueKey subAcctUniqKey = new AppUidUniqueKey(mainAcctAppUidKey.appCode(), Uid.randomUidOnEmpty(subAcctUid));
        Map<String, Object> preference = siteConfigService.restoreSiteConfig(curSite, "default.preference", new HashMap<>());
        IamAccount subIamAccount = new IamAccount(
                subAcctUniqKey, tenantCode, subAcctParentUid, subAcctSiteDomain, Collections.singleton(curSite),
                credential, AcctStatus.NOT_ACTIVE, nickname, avatar, region,
                true, extraProps, new AcctActivityRecord(),
                Collections.singleton(new AcctSiteProfile(curSite, roles, lang, preference))
        );
        EventBus.fire(mainAcct, new SubAcctCreatedEvent(subAcctUniqKey, mainAcct.nickname, mainAcctAppUidKey.uid(), credential));
        return subIamAccount;
    }

    void deleteSubAcct(IamAccount mainAcct, IamAccount subAcct) {
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
        iamAccountRepository.softDelete(subAcct.appUidKey);
    }

}
