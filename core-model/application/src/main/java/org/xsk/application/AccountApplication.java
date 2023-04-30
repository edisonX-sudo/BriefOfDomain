package org.xsk.application;

import org.xsk.domain.account.*;

public class AccountApplication {
    AccountFactory accountFactory;
    AccountRepo accountRepo;
    AccountPrivilegeService accountPrivilegeService;

    public AccountId create(AccountStatus status, String name, String loginName, String password, Contact contact, PhysicalAddress address, AccountId parentAccountId) {
        Account newAccount = accountFactory.build(status, name, loginName, password, contact, address, parentAccountId);
        accountRepo.save(newAccount);
        return newAccount.id();
    }

    public AccountId createdSubAccount(AccountId accountId, String name, String loginName, String password, Contact contact, PhysicalAddress address) {
        Account mainAccount = accountRepo.findNotNone(accountId);
        Account subAccount = mainAccount.createSubAccount(name, loginName, password, contact, address);
        accountRepo.save(subAccount);
        return subAccount.id();
    }

    public void handoverMainPrivilege(AccountId mainAccountId,AccountId subAccountId){
        Account mainAccount = accountRepo.findNotNone(mainAccountId);
        Account subAccount = accountRepo.findNotNone(subAccountId);
        mainAccount.handoverMainPrivilege(subAccount,accountPrivilegeService);
    }
}
