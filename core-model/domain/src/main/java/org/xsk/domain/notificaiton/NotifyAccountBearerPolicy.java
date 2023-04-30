package org.xsk.domain.notificaiton;

import org.xsk.domain.account.Contact;
import org.xsk.domain.account.event.AccountCreated;
import org.xsk.domain.common.DomainPolicy;

public abstract class NotifyAccountBearerPolicy extends DomainPolicy {

    public void subscribe(AccountCreated accountCreated) {
        Contact contact = accountCreated.contact();
        notifyBearer(contact);
    }

    protected abstract void notifyBearer(Contact contact);

}
