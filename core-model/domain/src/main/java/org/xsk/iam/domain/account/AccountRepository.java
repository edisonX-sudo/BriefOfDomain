package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainRepository;

public abstract class AccountRepository extends DomainRepository<Account, AppUidUniqueKey> {
    abstract void softDelete( AppUidUniqueKey subAcctKey);
}
