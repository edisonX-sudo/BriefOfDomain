package org.xsk.domain.account.event;

import org.xsk.domain.account.AccountId;
import org.xsk.domain.account.Contact;
import org.xsk.domain.common.DomainEvent;

import java.util.function.Supplier;

public class AccountCreated extends DomainEvent.EntityCreatedEvent<AccountId> {

    private final Contact contact;

    public AccountCreated(Supplier<AccountId> idSupplier, Contact contact) {
        super(idSupplier);
        this.contact = contact;
    }

    public Contact contact() {
        return contact;
    }
}
