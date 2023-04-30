package org.xsk.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class OnlyMainAccountOperate extends DomainException {
    public OnlyMainAccountOperate() {
        super("only main account can operate");
    }
}
