package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class AcctEmailExistException extends DomainException {
    public AcctEmailExistException() {
        super("account email exists");
    }
}
