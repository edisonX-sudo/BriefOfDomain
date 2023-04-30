package org.xsk.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class IllegalAccountStateException extends DomainException {
    public IllegalAccountStateException(String message) {
        super(message);
    }
}
