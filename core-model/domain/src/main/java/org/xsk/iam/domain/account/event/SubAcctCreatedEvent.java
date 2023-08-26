package org.xsk.iam.domain.account.event;

import org.xsk.domain.common.DomainEvent;
import org.xsk.iam.domain.account.AppUidUniqueKey;

public class SubAcctCreatedEvent extends DomainEvent {

    private final AppUidUniqueKey subAcctUniqKey;

    public SubAcctCreatedEvent(AppUidUniqueKey subAcctUniqKey) {
        this.subAcctUniqKey = subAcctUniqKey;
    }
}
