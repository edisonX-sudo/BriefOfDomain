package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class AcctLoginNameExistException extends DomainException {
    public AcctLoginNameExistException() {
        super("account email exists");
    }
}
