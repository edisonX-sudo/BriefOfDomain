package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class OnlyMainAcctCanOperateException extends DomainException {
    public OnlyMainAcctCanOperateException() {
        super("only main acct can operate");
    }
}
