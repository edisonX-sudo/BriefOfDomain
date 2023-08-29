package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class AcctUidExistException extends DomainException {
    public AcctUidExistException() {
        super("account uid exists");
    }
}
