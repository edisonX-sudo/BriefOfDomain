package org.xsk.application;

import lombok.RequiredArgsConstructor;
import org.xsk.domain.account.*;
import org.xsk.domain.common.DomainApplication;

@RequiredArgsConstructor
public class AccountApplication extends DomainApplication {
    final AccountFactory accountFactory;
    final AccountRepo accountRepo;
    final AccountPrivilegeService accountPrivilegeService;
    final AuthService authService;

    public AccountId create(AccountStatus status, String name, String loginName, String password, Contact contact, PhysicalAddress address) {
        return tx(() -> {
            Account newAccount = accountFactory.build(status, name, loginName, password, contact, address);
            accountRepo.save(newAccount);
            return newAccount.id();
        });

    }

    public AccountId createdSubAccount(AccountId accountId, String name, String loginName, String password, Contact contact, PhysicalAddress address) {
        return tx(() -> {
            Account mainAccount = accountRepo.findNotNone(accountId);
            Account subAccount = mainAccount.createSubAccount(name, loginName, password, contact, address);
            accountRepo.save(subAccount);
            return subAccount.id();
        });
    }

    public void handoverMainPrivilege(AccountId mainAccountId, AccountId subAccountId) {
        tx(() -> {
            Account mainAccount = accountRepo.findNotNone(mainAccountId);
            Account subAccount = accountRepo.findNotNone(subAccountId);
            mainAccount.handoverMainPrivilege(subAccount, accountPrivilegeService);
        });
    }

    public void auth(String token) {
        // high rate invocation here
        authService.validateAuth(token);
    }
}
