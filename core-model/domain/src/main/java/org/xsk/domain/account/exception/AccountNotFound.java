package org.xsk.domain.account.exception;

import org.xsk.domain.common.Id;
import org.xsk.domain.common.NotFoundEntityDomainException;

public class AccountNotFound extends NotFoundEntityDomainException {

    public AccountNotFound(Id<?> id) {
        super(id, "account not found");
    }

    public AccountNotFound() {
        super("account not found");
    }
}
