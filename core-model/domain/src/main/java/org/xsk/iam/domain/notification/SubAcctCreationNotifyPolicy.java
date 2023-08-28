package org.xsk.iam.domain.notification;

import org.xsk.domain.common.DomainPolicy;
import org.xsk.iam.domain.account.event.SubAcctCreatedEvent;

public class SubAcctCreationNotifyPolicy extends DomainPolicy<SubAcctCreatedEvent> {
    @Override
    public void subscribe(SubAcctCreatedEvent subAcctCreatedEvent) {
        // TODO: 2023/8/27 notify sub acct owner
    }

    @Override
    public SubscribePoint subscribePoint() {
        return SubscribePoint.AFTER_MAIN_PROCESS_COMPLETED;
    }

    @Override
    protected Class<SubAcctCreatedEvent> eventClass() {
        return null;
    }
}
