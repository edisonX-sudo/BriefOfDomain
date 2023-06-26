package org.xsk.domain.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.xsk.domain.account.event.AccountCreated;
import org.xsk.domain.common.DomainFactory;
import org.xsk.domain.common.EventBus;


@AllArgsConstructor
@Component
public class AccountFactory extends DomainFactory {

    public Account build(AccountStatus status, String name, String loginName, String password, Contact contact, PhysicalAddress address, AccountId parentAccountId) {
        Account account = new Account(status, name, loginName, password, contact, address, parentAccountId);
        EventBus.fire(new AccountCreated(() -> account.accountId, contact));
        return account;
    }
}
