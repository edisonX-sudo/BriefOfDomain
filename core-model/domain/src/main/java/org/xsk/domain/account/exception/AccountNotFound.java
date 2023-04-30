package org.xsk.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class AccountNotFound extends DomainException {
    public AccountNotFound() {
        super("account not found");
    }
}
