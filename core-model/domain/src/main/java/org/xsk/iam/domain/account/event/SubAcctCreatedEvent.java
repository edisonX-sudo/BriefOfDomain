package org.xsk.iam.domain.account.event;

import org.xsk.domain.common.DomainEvent;
import org.xsk.iam.domain.account.AppUidUniqueKey;
import org.xsk.iam.domain.account.Credential;
import org.xsk.iam.domain.account.Uid;

public class SubAcctCreatedEvent extends DomainEvent.EntityCreatedEvent<AppUidUniqueKey> {
    private final Credential subAcctCredential;
    private final String mainAcctNickname;
    private final Uid mainAcctUid;

    public SubAcctCreatedEvent(AppUidUniqueKey id, String mainAcctNickname, Uid mainAcctUid, Credential subAcctCredential) {
        super(id);
        this.mainAcctUid = mainAcctUid;
        this.mainAcctNickname = mainAcctNickname;
        this.subAcctCredential = subAcctCredential;
    }

    public Credential subAcctCredential() {
        return subAcctCredential;
    }

    public String mainAcctNickname() {
        return mainAcctNickname;
    }

    public Uid mainAcctUid() {
        return mainAcctUid;
    }
}
