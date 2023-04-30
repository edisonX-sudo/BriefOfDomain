package org.xsk.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class NotCreatedByMainAccount extends DomainException {
    public NotCreatedByMainAccount() {
        super("only main account can create subaccount");
    }
}
