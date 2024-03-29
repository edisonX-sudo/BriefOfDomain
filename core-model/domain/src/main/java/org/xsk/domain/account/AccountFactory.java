package org.xsk.domain.account;

import lombok.AllArgsConstructor;
import org.xsk.domain.account.event.AccountCreated;
import org.xsk.domain.common.DomainFactory;
import org.xsk.domain.common.EventBus;


@AllArgsConstructor
public class AccountFactory extends DomainFactory {

    public Account build(AccountStatus status, String name, String loginName, String password, Contact contact, PhysicalAddress address) {
        Account account = new Account(status, name, loginName, password, contact, address, null);
        EventBus.fire(new AccountCreated(() -> account.accountId, contact));
        return account;
    }
}
