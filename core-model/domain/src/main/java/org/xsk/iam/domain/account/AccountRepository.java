package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainRepository;
import org.xsk.iam.domain.app.TenantCode;

public abstract class AccountRepository extends DomainRepository<Account, AppUidUniqueKey> {
    abstract void softDelete(AppUidUniqueKey id);

    abstract Integer countSiteSubAcct(AppUidUniqueKey id, String subAcctSiteDomain);

    abstract boolean existLoginName(AppUidUniqueKey mainAcctAppUidKey, TenantCode tenantCode, Uid subAcctParentUid, String subAcctSiteDomain, String loginName);

    abstract boolean existUid(AppUidUniqueKey mainAcctAppUidKey, TenantCode tenantCode);

    abstract boolean existEmail(AppUidUniqueKey mainAcctAppUidKey, TenantCode tenantCode, Uid subAcctParentUid, String subAcctSiteDomain, String email);

    abstract boolean existMobile(AppUidUniqueKey mainAcctAppUidKey, TenantCode tenantCode, Uid subAcctParentUid, String subAcctSiteDomain, String mobile);
}
