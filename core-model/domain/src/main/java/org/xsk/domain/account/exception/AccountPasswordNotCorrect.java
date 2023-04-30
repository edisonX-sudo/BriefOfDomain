package org.xsk.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class AccountPasswordNotCorrect extends DomainException {
    public AccountPasswordNotCorrect() {
        super("account password not correct");
    }
}
