package org.xsk.iam.domain.account;

import org.xsk.domain.common.NotFoundEntityDomainException;
import org.xsk.iam.domain.account.exception.AcctNotFoundException;
import org.xsk.iam.domain.app.TenantCode;

public class IamAccountRepositoryImpl extends IamAccountRepository {
    @Override
    void softDelete(AppUidUniqueKey id) {

    }

    @Override
    Integer countSiteSubAcct(AppUidUniqueKey id, String subAcctSiteDomain) {
        return 0;
    }

    @Override
    boolean existLoginName(AppUidUniqueKey mainAcctAppUidKey, TenantCode tenantCode, Uid subAcctParentUid, String subAcctSiteDomain, String loginName) {
        return false;
    }

    @Override
    boolean existUid(AppUidUniqueKey mainAcctAppUidKey, TenantCode tenantCode) {
        return false;
    }

    @Override
    boolean existEmail(AppUidUniqueKey mainAcctAppUidKey, TenantCode tenantCode, Uid subAcctParentUid, String subAcctSiteDomain, String email) {
        return false;
    }

    @Override
    boolean existMobile(AppUidUniqueKey mainAcctAppUidKey, TenantCode tenantCode, Uid subAcctParentUid, String subAcctSiteDomain, String mobile) {
        return false;
    }

    @Override
    protected IamAccount findInternal(AppUidUniqueKey id) {
        return null;
    }

    @Override
    protected NotFoundEntityDomainException notFoundException(AppUidUniqueKey id) {
        return new AcctNotFoundException();
    }

    @Override
    protected void saveInternal(IamAccount entity) {

    }
}
