package org.xsk.domain.account.exception;

import org.xsk.domain.common.NotFoundEntityDomainException;

public class AccountNotFound extends NotFoundEntityDomainException {
    public AccountNotFound() {
        super("account not found");
    }
}
