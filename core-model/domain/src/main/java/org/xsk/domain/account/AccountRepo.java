package org.xsk.domain.account;

import org.xsk.domain.common.Repository;

import java.util.List;
import java.util.Set;

public abstract class AccountRepo extends Repository<Account, AccountId> {

    public abstract Account find(String loginName);

    /**
     * 含主账号和所有子账号的账号集合
     * @param mainAccountId
     * @return
     */
    public abstract List<Account> findAccountGroup(AccountId mainAccountId);

    public abstract void saveAll(Set<Account> accounts);
}
