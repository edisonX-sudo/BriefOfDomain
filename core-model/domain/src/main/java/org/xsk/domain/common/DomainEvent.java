package org.xsk.domain.common;

import java.util.Date;

public abstract class DomainEvent {

    private final long occurredAt;

    public DomainEvent() {
        occurredAt = System.currentTimeMillis();
    }

    public long occurredAt() {
        return occurredAt;
    }

    public RecordInfo recordInfo() {
        return RecordInfo.NONE;
    }

    enum RecordInfo {
        NONE,
        TSDB,
    }
}
