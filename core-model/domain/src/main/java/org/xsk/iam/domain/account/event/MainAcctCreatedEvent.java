package org.xsk.iam.domain.account.event;

import org.xsk.domain.common.DomainEvent;
import org.xsk.iam.domain.account.AppUidUniqueKey;

public class MainAcctCreatedEvent extends DomainEvent {

    private final AppUidUniqueKey subAcctUniqKey;

    public MainAcctCreatedEvent(AppUidUniqueKey subAcctUniqKey) {
        this.subAcctUniqKey = subAcctUniqKey;
    }
}
