package org.xsk.domain.account;

import org.xsk.domain.common.DomainRepository;

import java.util.List;

public abstract class AccountRepo extends DomainRepository<Account, AccountId> {

    public abstract Account find(String loginName);

    /**
     * 含主账号和所有子账号的账号集合
     * @param mainAccountId
     * @return
     */
    public abstract List<Account> findAccountGroup(AccountId mainAccountId);

}
