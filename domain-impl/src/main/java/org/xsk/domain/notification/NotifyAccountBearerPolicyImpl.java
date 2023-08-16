package org.xsk.domain.notification;

import org.xsk.domain.account.Contact;
import org.xsk.domain.notificaiton.NotifyAccountBearerPolicy;

//@Component
public class NotifyAccountBearerPolicyImpl extends NotifyAccountBearerPolicy {
    @Override
    protected void notifyBearer(Contact contact) {
        System.out.println("notify account bearer via contact, " + contact);
        // TODO: 2023/4/14 notify account bearer via contact
    }
}
