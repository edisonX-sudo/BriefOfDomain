package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class OnlyParentAcctCanOperateException extends DomainException {
    public OnlyParentAcctCanOperateException() {
        super("only parent acct can operate");
    }
}
