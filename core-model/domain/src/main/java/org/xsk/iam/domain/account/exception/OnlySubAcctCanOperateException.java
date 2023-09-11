package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class OnlySubAcctCanOperateException extends DomainException {
    public OnlySubAcctCanOperateException() {
        super("only sub acct can operate");
    }
}
