package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class AcctMobileExistException extends DomainException {
    public AcctMobileExistException() {
        super("account email exists");
    }
}
