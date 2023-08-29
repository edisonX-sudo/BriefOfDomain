package org.xsk.iam.domain.account.exception;

import org.xsk.domain.common.DomainException;

public class SubAcctCountOverLimit extends DomainException {

    public SubAcctCountOverLimit() {
        super("subAccount count over limit");
    }
}
