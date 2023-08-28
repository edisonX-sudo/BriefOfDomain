package org.xsk.iam.domain.account;

import org.xsk.domain.common.NotFoundEntityDomainException;

public class AccountRepositoryImpl extends AccountRepository{
    @Override
    void softDelete(AppUidUniqueKey subAcctKey) {

    }

    @Override
    protected Account findInternal(AppUidUniqueKey id) {
        return null;
    }

    @Override
    protected NotFoundEntityDomainException notFoundException(AppUidUniqueKey id) {
        return null;
    }

    @Override
    protected void saveInternal(Account entity) {

    }
}
