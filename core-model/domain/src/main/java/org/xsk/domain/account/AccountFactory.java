package org.xsk.domain.account;

import lombok.AllArgsConstructor;
import org.xsk.domain.account.event.AccountCreated;
import org.xsk.domain.common.EventBus;

import static org.xsk.domain.account.Account.newAccount;

@AllArgsConstructor
public class AccountFactory {

    public Account build(AccountStatus status, String name, String loginName, String password, Contact contact, PhysicalAddress address, AccountId parentAccountId) {
        Account account = newAccount(status, name, loginName, password, contact, address, parentAccountId);
        EventBus.fire(new AccountCreated(() -> account.accountId,contact));
        return account;
    }
}
