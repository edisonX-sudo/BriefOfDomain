package org.xsk.domain.common;

public abstract class DomainPolicy {

    public SubscribePoint subscribePoint() {
        return SubscribePoint.BEFORE_DATA_COMMIT;
    }

    enum SubscribePoint {
        BEFORE_DATA_COMMIT, AFTER_DATA_COMMIT, ASYNC
    }
}
