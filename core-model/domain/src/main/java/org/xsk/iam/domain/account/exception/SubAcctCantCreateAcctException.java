package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class SubAcctCantCreateAcctException extends DomainException {
    public SubAcctCantCreateAcctException() {
        super("subAccount cant create account");
    }
}
