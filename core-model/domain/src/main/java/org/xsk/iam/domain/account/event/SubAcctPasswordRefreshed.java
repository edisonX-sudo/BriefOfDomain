package org.xsk.iam.domain.account.event;

import org.xsk.domain.common.DomainEvent;
import org.xsk.iam.domain.account.AppUidUniqueKey;
import org.xsk.iam.domain.account.Credential;

public class SubAcctPasswordRefreshed extends DomainEvent {

    private final AppUidUniqueKey parentKey;
    private final Credential credential;

    public SubAcctPasswordRefreshed(AppUidUniqueKey parentKey, Credential credential) {
        this.parentKey = parentKey;
        this.credential = credential;
    }
}
