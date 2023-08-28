package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.NotFoundEntityDomainException;

public class AcctNotFoundException extends NotFoundEntityDomainException {
    public AcctNotFoundException() {
        super("account not found");
    }
}
