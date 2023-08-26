package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class AcctCantLoginException extends DomainException {
    public AcctCantLoginException() {
        super("account cant login");
    }
}
