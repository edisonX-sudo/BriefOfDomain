package org.xsk.iam.domain.account.event;

import org.xsk.domain.common.DomainEvent;
import org.xsk.iam.domain.account.AppUidUniqueKey;

public class MainAcctCreatedEvent extends DomainEvent.EntityCreatedEvent<AppUidUniqueKey>{

    public MainAcctCreatedEvent(AppUidUniqueKey id) {
        super(id);
    }
}
