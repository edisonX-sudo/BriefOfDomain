package org.xsk.iam.domain.account.event;

import org.xsk.domain.common.DomainEvent;
import org.xsk.iam.domain.account.AppUidUniqueKey;

public class SubAcctCreatedEvent extends DomainEvent.EntityCreatedEvent<AppUidUniqueKey> {

    public SubAcctCreatedEvent(AppUidUniqueKey id) {
        super(id);
    }
}
