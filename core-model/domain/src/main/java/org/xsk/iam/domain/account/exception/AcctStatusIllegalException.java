package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class AcctStatusIllegalException extends DomainException {
    public AcctStatusIllegalException() {
        super("account status illegal");
    }
}
