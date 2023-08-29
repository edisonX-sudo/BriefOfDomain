package org.xsk.iam.domain.account;

import org.xsk.domain.common.NotFoundEntityDomainException;
import org.xsk.iam.domain.account.exception.AcctNotFoundException;

public class AccountRepositoryImpl extends AccountRepository{
    @Override
    void softDelete(AppUidUniqueKey id) {

    }

    @Override
    Integer countSiteSubAcct(AppUidUniqueKey id, String subAcctSiteDomain) {
        return 0;
    }

    @Override
    protected Account findInternal(AppUidUniqueKey id) {
        return null;
    }

    @Override
    protected NotFoundEntityDomainException notFoundException(AppUidUniqueKey id) {
        return new AcctNotFoundException();
    }

    @Override
    protected void saveInternal(Account entity) {

    }
}
